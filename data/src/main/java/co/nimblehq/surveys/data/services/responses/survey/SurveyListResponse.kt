package co.nimblehq.surveys.data.services.responses.survey


import co.nimblehq.surveys.domain.extensions.defaultEmpty
import co.nimblehq.surveys.domain.extensions.defaultZero
import co.nimblehq.surveys.domain.models.survey.SurveyPageModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SurveyListResponse(
    @Json(name = "data")
    val data: List<Data>?,
    @Json(name = "meta")
    val meta: Meta?
)

fun SurveyListResponse.toSurveyPageModel() = SurveyPageModel(
    page = meta?.page.defaultZero(),
    totalPages = meta?.pages.defaultZero(),
    surveyList = data?.map { data -> data.toSurveyModel() }.defaultEmpty(),
)