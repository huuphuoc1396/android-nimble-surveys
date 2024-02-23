package co.nimblehq.surveys.data.services.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataResponse<T>(
    @Json(name = "data")
    val data: T?,
)
