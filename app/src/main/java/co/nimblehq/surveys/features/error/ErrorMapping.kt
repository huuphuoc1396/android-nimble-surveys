package co.nimblehq.surveys.features.error

import android.content.Context
import co.nimblehq.surveys.R
import co.nimblehq.surveys.domain.extensions.defaultEmpty
import co.nimblehq.surveys.domain.models.errors.ApiError
import co.nimblehq.surveys.extensions.showToast

fun Throwable.userReadableMessage(context: Context): String {
    return when (this) {
        is ApiError -> when (this) {
            ApiError.NoConnection -> context.getString(R.string.no_connection)

            is ApiError.Server -> serverMsg.ifEmpty { context.getString(R.string.unknown_error) }

            ApiError.Unauthorized -> context.getString(R.string.unauthorized_error)
        }

        else -> message.defaultEmpty()
    }
}

fun Throwable.showToast(context: Context) =
    context.showToast(userReadableMessage(context))
