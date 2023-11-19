package co.nimblehq.surveys.features.error

import android.content.Context
import co.nimblehq.surveys.R
import co.nimblehq.surveys.domain.errors.exceptions.network.NetworkCaughtException
import co.nimblehq.surveys.domain.errors.exceptions.unknown.UnknownCaughtException
import co.nimblehq.surveys.extensions.showToast

fun Throwable.userReadableMessage(context: Context): String {
    return when (this) {
        is NetworkCaughtException -> when (this) {
            NetworkCaughtException.NoConnection -> context.getString(R.string.no_connection)

            is NetworkCaughtException.Server -> serverMsg
        }

        is UnknownCaughtException -> context.getString(R.string.unknown_error)

        else -> message.orEmpty()
    }
}

fun Throwable.showToast(context: Context) =
    context.showToast(userReadableMessage(context))
