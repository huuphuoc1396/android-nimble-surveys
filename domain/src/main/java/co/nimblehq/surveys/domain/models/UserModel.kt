package co.nimblehq.surveys.domain.models

data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String,
)