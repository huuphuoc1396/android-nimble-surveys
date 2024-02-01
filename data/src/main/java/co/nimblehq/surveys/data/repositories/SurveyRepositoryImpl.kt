package co.nimblehq.surveys.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import co.nimblehq.surveys.data.repositories.constants.SurveyPagingConfigs
import co.nimblehq.surveys.data.mapper.toSurveyModel
import co.nimblehq.surveys.data.repositories.medidator.SurveyRemoteMediator
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
                pageSize = SurveyPagingConfigs.PAGE_SIZE,
                prefetchDistance = SurveyPagingConfigs.PREFECT_DISTANCE,
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                surveyDao.getSurveys()
            },
        ).flow.map { surveyPagingData ->
            surveyPagingData.map { surveyEntity ->
                surveyEntity.toSurveyModel()
            }
        }
        return pagingData
    }
}