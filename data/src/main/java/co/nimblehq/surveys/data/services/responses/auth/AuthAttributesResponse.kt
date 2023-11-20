package co.nimblehq.surveys.data.services.responses.auth


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthAttributesResponse(
    @Json(name = "access_token")
    val accessToken: String?,
    @Json(name = "created_at")
    val createdAt: Int?,
    @Json(name = "expires_in")
    val expiresIn: Int?,
    @Json(name = "refresh_token")
    val refreshToken: String?,
    @Json(name = "token_type")
    val tokenType: String?
) {

    val authToken: String?
        get() = if (tokenType != null && accessToken != null) "$tokenType $accessToken" else null
}