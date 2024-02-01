package co.nimblehq.surveys.data.repositories.medidator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import co.nimblehq.surveys.data.mapper.toSurveyEntities
import co.nimblehq.surveys.data.mapper.toSurveyKeyEntities
import co.nimblehq.surveys.data.repositories.constants.SurveyPagingConfigs.CACHE_TIME_OUT_HOUR
import co.nimblehq.surveys.data.repositories.constants.SurveyPagingConfigs.PAGE_SIZE
import co.nimblehq.surveys.data.services.AuthApiService
import co.nimblehq.surveys.data.services.responses.survey.toSurveyPageModel
import co.nimblehq.surveys.data.storages.database.dao.SurveyDao
import co.nimblehq.surveys.data.storages.database.dao.SurveyKeyDao
import co.nimblehq.surveys.data.storages.database.entity.SurveyEntity
import co.nimblehq.surveys.data.storages.database.entity.SurveyKeyEntity
import co.nimblehq.surveys.domain.extensions.defaultZero
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SurveyRemoteMediator @Inject constructor(
    private val apiService: AuthApiService,
    private val surveyDao: SurveyDao,
    private val surveyKeyDao: SurveyKeyDao,
) : RemoteMediator<Int, SurveyEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SurveyEntity>
    ): MediatorResult {
        val currentPage = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }

            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextPage ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null,
                )
                nextKey
            }
        }
        try {
            val surveyPageResult = apiService.getSurveyList(
                page = currentPage,
                size = PAGE_SIZE,
            ).toSurveyPageModel()
            val surveyList = surveyPageResult.surveyList
            val totalPages = surveyPageResult.totalPages
            val endOfPaginationReached = surveyList.isEmpty() || currentPage >= totalPages
            val needToDeleteDatabase = loadType == LoadType.REFRESH

            val nextPage = if (endOfPaginationReached) null else currentPage + 1
            val surveyKeys = surveyList.toSurveyKeyEntities(nextPage)

            surveyKeyDao.insertAndDeleteSurveyKey(
                needToDelete = needToDeleteDatabase,
                surveyKeys = surveyKeys,
            )
            surveyDao.insertAndDeleteSurvey(
                needToDelete = needToDeleteDatabase,
                surveys = surveyList.toSurveyEntities(),
            )
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (throwable: Throwable) {
            return MediatorResult.Error(throwable)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(CACHE_TIME_OUT_HOUR, TimeUnit.HOURS)
        val currentTimeMillis = System.currentTimeMillis()
        val creationTime = surveyKeyDao.getCreationTime().defaultZero()
        val shouldRefresh = currentTimeMillis - creationTime < cacheTimeout
        return if (shouldRefresh) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, SurveyEntity>): SurveyKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.surveyId?.let { id ->
                surveyKeyDao.getSurveyKey(id)
            }
        }
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, SurveyEntity>): SurveyKeyEntity? {
        return state.pages
            .lastOrNull { page ->
                page.data.isNotEmpty()
            }?.data?.lastOrNull()
            ?.let { survey ->
                surveyKeyDao.getSurveyKey(survey.surveyId)
            }
    }
}