package co.nimblehq.surveys.data.services.responses.error

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ErrorsResponse(
    @Json(name = "errors")
    val errors: List<Error>?,
) {

    @JsonClass(generateAdapter = true)
    data class Error(
        @Json(name = "code")
        val code: String?,
        @Json(name = "detail")
        val detail: String?,
        @Json(name = "source")
        val source: String?
    )
}
