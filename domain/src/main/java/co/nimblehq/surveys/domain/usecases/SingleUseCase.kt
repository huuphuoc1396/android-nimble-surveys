package co.nimblehq.surveys.domain.usecases

import co.nimblehq.surveys.domain.errors.exceptions.CaughtException
import co.nimblehq.surveys.domain.errors.mappers.ErrorMapper
import co.nimblehq.surveys.domain.functional.safeSuspend
import kotlinx.coroutines.CoroutineDispatcher

abstract class SingleUseCase<in P, out R>(
    private val dispatcher: CoroutineDispatcher,
    private val errorMapper: ErrorMapper<CaughtException>,
) : UseCase<P, R> {

    suspend operator fun invoke(
        params: P,
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