package co.nimblehq.surveys.features.survey.list

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import co.nimblehq.surveys.data.mapper.toSurveyEntities
import co.nimblehq.surveys.data.storages.database.dao.SurveyDao
import co.nimblehq.surveys.data.storages.database.dao.SurveyKeyDao
import co.nimblehq.surveys.data.storages.database.entity.SurveyEntity
import co.nimblehq.surveys.data.storages.database.entity.SurveyKeyEntity
import co.nimblehq.surveys.domain.extensions.defaultZero
import co.nimblehq.surveys.domain.usecases.survey.GetSurveyListUseCase

@OptIn(ExperimentalPagingApi::class)
class SurveyRemoteMediator(
    private val getSurveyListUseCase: GetSurveyListUseCase,
    private val surveyDao: SurveyDao,
    private val surveyKeyDao: SurveyKeyDao,
) : RemoteMediator<Int, SurveyEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SurveyEntity>
    ): MediatorResult {
        val currentPage = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state, surveyKeyDao)
                if (remoteKey?.nexPage == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKey.nexPage.defaultZero()
            }
        }
        val surveyPageResult = getSurveyListUseCase(
            params = GetSurveyListUseCase.Params(currentPage, SurveyPageConfig.PAGE_SIZE)
        )

        surveyPageResult.fold(
            onSuccess = {
                val surveyList = it.surveyList
                val endOfPaginationReached = surveyList.isEmpty()

                if (loadType == LoadType.REFRESH) {
                    surveyDao.deleteSurveys()
                    surveyKeyDao.deleteSurveyKeys()
                }
                val nextPage = if (endOfPaginationReached) null else currentPage + 1
                val surveyKeys = surveyList.map { survey ->
                    SurveyKeyEntity(
                        surveyId = survey.id,
                        nexPage = nextPage
                    )
                }

                surveyDao.insertAll(surveyList.toSurveyEntities())
                surveyKeyDao.insert(surveyKeys)
                return MediatorResult.Success(endOfPaginationReached = surveyList.isEmpty())
            },
            onFailure = {
                return MediatorResult.Error(it)
            }
        )
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, SurveyEntity>,
        surveyKeyDao: SurveyKeyDao
    ): SurveyKeyEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { survey ->
            surveyKeyDao.getSurveyKey(survey.surveyId)
        }
    }
}