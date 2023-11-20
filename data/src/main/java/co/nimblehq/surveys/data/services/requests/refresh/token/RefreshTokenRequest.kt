package co.nimblehq.surveys.data.services.requests.refresh.token


import co.nimblehq.surveys.data.services.requests.ClientRequest
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshTokenRequest(
    @Json(name = "refresh_token")
    val refreshToken: String,
    @Json(name = "grant_type")
    val grantType: String = "refresh_token",
) : ClientRequest()