package co.nimblehq.surveys.data.services.responses.error

import com.squareup.moshi.Json

data class Error404Response(
    @Json(name = "status")
    val status: Int,
    @Json(name = "error")
    val error: String,
)
