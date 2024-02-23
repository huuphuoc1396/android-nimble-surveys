package co.nimblehq.surveys.domain.repositories

import androidx.paging.PagingData
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    suspend fun getSurveyList(): Flow<PagingData<SurveyModel>>
}