package co.nimblehq.surveys.domain.usecases

import co.nimblehq.surveys.domain.functional.safeSuspend
import kotlinx.coroutines.CoroutineDispatcher

abstract class SingleUseCase<in P, out R>(
    private val dispatcher: CoroutineDispatcher,
) : UseCase<P, R> {

    suspend operator fun invoke(params: P): Result<R> {
        return safeSuspend(dispatcher = dispatcher) {
            execute(params)
        }
    }
}