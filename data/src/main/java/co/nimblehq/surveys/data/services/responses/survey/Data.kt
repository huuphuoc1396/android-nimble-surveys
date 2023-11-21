package co.nimblehq.surveys.data.services.responses.survey


import co.nimblehq.surveys.domain.extensions.defaultEmpty
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "attributes")
    val attributes: Attributes?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "type")
    val type: String?
)

fun Data.toSurveyModel() = SurveyModel(
    id = id.defaultEmpty(),
    title = attributes?.title.defaultEmpty(),
    description = attributes?.description.defaultEmpty(),
    coverUrl = attributes?.coverImageUrl.defaultEmpty()
)