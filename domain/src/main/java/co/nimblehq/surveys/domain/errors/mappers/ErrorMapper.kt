package co.nimblehq.surveys.domain.errors.mappers

import co.nimblehq.surveys.domain.errors.exceptions.CaughtException

interface ErrorMapper<out E : CaughtException> {
    fun map(throwable: Throwable): E?
}