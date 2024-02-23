package co.nimblehq.surveys.domain.models.errors

sealed class ApiError : Throwable() {
    data object NoConnection : ApiError()

    data class Server(
        val code: Int,
        val serverMsg: String,
    ) : ApiError()

    data object Unauthorized : ApiError()
}
