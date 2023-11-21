package co.nimblehq.surveys.domain.models.survey

data class SurveyPageModel(
    val surveyList: List<SurveyModel>,
    val page: Int,
    val totalPages: Int,
)