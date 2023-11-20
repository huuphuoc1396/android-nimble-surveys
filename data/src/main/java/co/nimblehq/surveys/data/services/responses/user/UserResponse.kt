package co.nimblehq.surveys.data.services.responses.user


import co.nimblehq.surveys.domain.models.UserModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "attributes")
    val attributes: UserAttributesResponse?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "type")
    val type: String?
)

fun UserResponse.toUserModel() = UserModel(
    id = id.orEmpty(),
    name = attributes?.name.orEmpty(),
    email = attributes?.email.orEmpty(),
    avatarUrl = attributes?.avatarUrl.orEmpty()

)