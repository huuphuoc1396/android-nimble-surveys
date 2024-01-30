package co.nimblehq.surveys.domain.usecases.survey

import co.nimblehq.surveys.domain.di.annotations.IoDispatcher
import co.nimblehq.surveys.domain.models.survey.SurveyPageModel
import co.nimblehq.surveys.domain.repositories.SurveyRepository
import co.nimblehq.surveys.domain.usecases.SingleUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetSurveyListUseCase
    @Inject
    constructor(
        private val surveyRepository: SurveyRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher,
    ) : SingleUseCase<GetSurveyListUseCase.Params, SurveyPageModel>(dispatcher) {
        data class Params(
            val page: Int,
            val size: Int,
        )

        override suspend fun execute(params: Params): SurveyPageModel {
            return surveyRepository.getSurveyList(
                page = params.page,
                size = params.size,
            )
        }
    }
