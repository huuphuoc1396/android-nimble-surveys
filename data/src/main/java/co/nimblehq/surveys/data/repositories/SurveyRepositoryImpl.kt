package co.nimblehq.surveys.data.repositories

import co.nimblehq.surveys.data.services.AuthApiService
import co.nimblehq.surveys.data.services.responses.survey.toSurveyPageModel
import co.nimblehq.surveys.domain.models.survey.SurveyPageModel
import co.nimblehq.surveys.domain.repositories.SurveyRepository
import javax.inject.Inject

class SurveyRepositoryImpl
    @Inject
    constructor(
        private val authApiService: AuthApiService,
    ) : SurveyRepository {
        override suspend fun getSurveyList(
            page: Int,
            size: Int,
        ): SurveyPageModel {
            val surveyListResponse = authApiService.getSurveyList(page, size)
            return surveyListResponse.toSurveyPageModel()
        }
    }
