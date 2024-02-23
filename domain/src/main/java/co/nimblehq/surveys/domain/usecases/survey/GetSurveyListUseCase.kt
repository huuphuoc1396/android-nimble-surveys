package co.nimblehq.surveys.domain.usecases.survey

import androidx.paging.PagingData
import co.nimblehq.surveys.domain.di.annotations.IoDispatcher
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import co.nimblehq.surveys.domain.repositories.SurveyRepository
import co.nimblehq.surveys.domain.usecases.EmptyParams
import co.nimblehq.surveys.domain.usecases.SingleUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSurveyListUseCase
    @Inject
    constructor(
        private val surveyRepository: SurveyRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher,
    ) : SingleUseCase<EmptyParams, Flow<PagingData<SurveyModel>>>(dispatcher) {
        data class Params(
            val page: Int,
            val size: Int,
        )

        override suspend fun execute(params: EmptyParams): Flow<PagingData<SurveyModel>> {
            return surveyRepository.getSurveyList()
        }
    }
