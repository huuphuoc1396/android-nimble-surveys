package co.nimblehq.surveys.domain.errors.exceptions.unknown

import co.nimblehq.surveys.domain.errors.exceptions.CaughtException

data class UnknownCaughtException(
    val unknown: Throwable,
) : CaughtException()