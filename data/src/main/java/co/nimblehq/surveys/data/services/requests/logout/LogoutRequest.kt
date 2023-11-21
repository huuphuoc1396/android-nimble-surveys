package co.nimblehq.surveys.data.services.requests.logout


import co.nimblehq.surveys.data.services.requests.ClientRequest
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogoutRequest(
    @Json(name = "token")
    val token: String,
) : ClientRequest()