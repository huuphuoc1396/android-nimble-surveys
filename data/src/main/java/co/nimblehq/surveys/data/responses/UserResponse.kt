package co.nimblehq.surveys.data.responses

import com.squareup.moshi.Json

data class UserResponse(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "email")
    val email: String?,
)
