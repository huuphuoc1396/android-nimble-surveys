package co.nimblehq.surveys.domain.errors.exceptions.network

import co.nimblehq.surveys.domain.errors.exceptions.CaughtException

sealed class NetworkCaughtException : CaughtException() {

    data object NoConnection : NetworkCaughtException()

    data class Server(
        val code: Int,
        val serverMsg: String,
    ) : NetworkCaughtException()
}