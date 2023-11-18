package co.nimblehq.surveys.domain.usecases

import co.nimblehq.surveys.domain.errors.exceptions.CaughtException
import co.nimblehq.surveys.domain.errors.mappers.ErrorMapper

interface UseCase<in P, out R> {
    suspend fun execute(
        params: P,
        errorMapper: ErrorMapper<CaughtException>,
    ): R
}

object EmptyParams