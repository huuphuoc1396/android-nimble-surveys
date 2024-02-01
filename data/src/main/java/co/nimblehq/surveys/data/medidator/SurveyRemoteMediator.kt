package co.nimblehq.surveys.data.medidator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import co.nimblehq.surveys.data.SurveyPagingConfigs.CACHE_TIME_OUT_HOUR
import co.nimblehq.surveys.data.SurveyPagingConfigs.SURVEY_LIST_PAGE_SIZE
import co.nimblehq.surveys.data.mapper.toSurveyEntities
import co.nimblehq.surveys.data.mapper.toSurveyKeyEntities
import co.nimblehq.surveys.data.services.AuthApiService
import co.nimblehq.surveys.data.services.responses.survey.toSurveyPageModel
import co.nimblehq.surveys.data.storages.database.dao.SurveyDao
import co.nimblehq.surveys.data.storages.database.dao.SurveyKeyDao
import co.nimblehq.surveys.data.storages.database.entity.SurveyEntity
import co.nimblehq.surveys.data.storages.database.entity.SurveyKeyEntity
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
                val nextKey = remoteKeys?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        try {
            val surveyPageResult = apiService.getSurveyList(
                    page = currentPage,
                    size = SURVEY_LIST_PAGE_SIZE
            ).toSurveyPageModel()
            val surveyList = surveyPageResult.surveyList
            val endOfPaginationReached = surveyList.isEmpty()
            val needToDeleteDatabase = loadType == LoadType.REFRESH

            val nextPage = if (endOfPaginationReached) null else currentPage + 1
            val surveyKeys = surveyList.toSurveyKeyEntities(nextPage)

            surveyKeyDao.insertAndDeleteSurveyKey(
                needToDelete = needToDeleteDatabase,
                surveyKeys =  surveyKeys,
            )
            surveyDao.insertAndDeleteSurvey(
                needToDelete = needToDeleteDatabase,
                surveys = surveyList.toSurveyEntities(),
            )
            return MediatorResult.Success(endOfPaginationReached = surveyList.isEmpty())
        } catch (throwable: Throwable) {
            return MediatorResult.Error(throwable)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(CACHE_TIME_OUT_HOUR, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (surveyKeyDao.getCreationTime()
                ?: 0) < cacheTimeout
        ) {
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
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { survey ->
            surveyKeyDao.getSurveyKey(survey.surveyId)
        }
    }
}