package co.nimblehq.surveys.data.services.requests.register


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterRequest(
    @Json(name = "user")
    val user: UserRequest,
)