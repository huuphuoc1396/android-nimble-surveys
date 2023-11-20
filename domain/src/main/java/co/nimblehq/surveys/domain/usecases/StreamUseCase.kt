package co.nimblehq.surveys.domain.usecases

import co.nimblehq.surveys.domain.errors.exceptions.CaughtException
import co.nimblehq.surveys.domain.errors.mappers.ErrorMapper
import co.nimblehq.surveys.domain.functional.safeFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

abstract class StreamUseCase<in P, out R>(
    private val dispatcher: CoroutineDispatcher,
    private val errorMapper: ErrorMapper<CaughtException>,
) : UseCase<P, Flow<R>> {

    suspend operator fun invoke(
        params: P,
    ): Flow<Result<R>> {
        return execute(
            params = params,
            errorMapper = errorMapper,
        ).safeFlow(
            errorMapper = errorMapper,
            dispatcher = dispatcher,
        )
    }
}