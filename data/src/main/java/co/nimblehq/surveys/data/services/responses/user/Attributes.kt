package co.nimblehq.surveys.data.services.responses.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Attributes(
    @Json(name = "avatar_url")
    val avatarUrl: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "name")
    val name: String?,
)
