package co.nimblehq.surveys.data.responses

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "message")
    val message: String?,
)
