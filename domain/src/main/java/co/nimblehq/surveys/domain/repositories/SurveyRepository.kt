package co.nimblehq.surveys.domain.repositories

import co.nimblehq.surveys.domain.models.survey.SurveyPageModel

interface SurveyRepository {

    suspend fun getSurveyList(page: Int, size: Int): SurveyPageModel
}