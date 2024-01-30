package co.nimblehq.surveys.data.services.responses.user

import co.nimblehq.surveys.domain.extensions.defaultEmpty
import co.nimblehq.surveys.domain.models.user.UserModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "attributes")
    val attributes: Attributes?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "type")
    val type: String?,
)

fun UserResponse.toUserModel() =
    UserModel(
        id = id.defaultEmpty(),
        name = attributes?.name.defaultEmpty(),
        email = attributes?.email.defaultEmpty(),
        avatarUrl = attributes?.avatarUrl.defaultEmpty(),
    )
