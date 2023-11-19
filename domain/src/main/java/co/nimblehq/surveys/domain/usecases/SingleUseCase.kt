package co.nimblehq.surveys.domain.usecases

import co.nimblehq.surveys.domain.errors.exceptions.CaughtException
import co.nimblehq.surveys.domain.errors.mappers.ErrorMapper
import co.nimblehq.surveys.domain.functional.safeSuspend
import kotlinx.coroutines.CoroutineDispatcher

abstract class SingleUseCase<in P, out R>(
    private val dispatcher: CoroutineDispatcher,
) : UseCase<P, R> {

    suspend operator fun invoke(
        params: P,
        errorMapper: ErrorMapper<CaughtException>,
    ): Result<R> {
        return safeSuspend(
            errorMapper = errorMapper,
            dispatcher = dispatcher,
        ) {
            execute(
                params = params,
                errorMapper = errorMapper,
            )
        }
    }
}