package co.nimblehq.surveys.domain.models.survey

data class SurveyModel(
    val id: String,
    val title: String,
    val description: String,
    val coverUrl: String,
    val totalRecords: Int,
)
