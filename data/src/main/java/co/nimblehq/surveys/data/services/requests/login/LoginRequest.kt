package co.nimblehq.surveys.data.services.requests.login


import co.nimblehq.surveys.data.services.requests.ClientRequest
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "grant_type")
    val grantType: String = "password",
) : ClientRequest()