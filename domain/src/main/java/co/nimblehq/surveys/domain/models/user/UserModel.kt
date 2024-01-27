package co.nimblehq.surveys.domain.models.user

data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String,
)