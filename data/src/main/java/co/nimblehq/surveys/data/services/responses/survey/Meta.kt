package co.nimblehq.surveys.data.services.responses.survey

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    @Json(name = "page")
    val page: Int?,
    @Json(name = "page_size")
    val pageSize: Int?,
    @Json(name = "pages")
    val pages: Int?,
    @Json(name = "records")
    val records: Int?,
)
