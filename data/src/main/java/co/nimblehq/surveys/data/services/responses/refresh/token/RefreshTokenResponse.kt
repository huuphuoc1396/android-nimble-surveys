package co.nimblehq.surveys.data.services.responses.refresh.token


import co.nimblehq.surveys.data.services.responses.auth.AuthAttributesResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshTokenResponse(
    @Json(name = "attributes")
    val attributes: AuthAttributesResponse?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "type")
    val type: String?
)