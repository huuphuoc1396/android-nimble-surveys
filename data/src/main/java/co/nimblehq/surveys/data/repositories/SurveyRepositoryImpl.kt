package co.nimblehq.surveys.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import co.nimblehq.surveys.data.Constants
import co.nimblehq.surveys.data.mapper.toSurveyModel
import co.nimblehq.surveys.data.medidator.SurveyRemoteMediator
import co.nimblehq.surveys.data.storages.database.dao.SurveyDao
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import co.nimblehq.surveys.domain.repositories.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val remoteMediator: SurveyRemoteMediator,
    private val surveyDao: SurveyDao,
) : SurveyRepository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getSurveyList(): Flow<PagingData<SurveyModel>> {
        val pagingData = Pager(
            config = PagingConfig(
                pageSize = Constants.SURVEY_LIST_PAGE_SIZE,
                prefetchDistance = 1,
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                surveyDao.getSurveys()
            },
        ).flow.map {
            it.map { surveyEntity ->
                surveyEntity.toSurveyModel()
            }
        }
        return pagingData
    }
}