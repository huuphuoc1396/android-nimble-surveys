package co.nimblehq.surveys.domain.functional

import co.nimblehq.surveys.domain.errors.exceptions.CaughtException
import co.nimblehq.surveys.domain.errors.exceptions.unknown.UnknownCaughtException
import co.nimblehq.surveys.domain.errors.mappers.ErrorMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

suspend fun <R> safeSuspend(
    errorMapper: ErrorMapper<CaughtException>,
    dispatcher: CoroutineDispatcher,
    action: suspend () -> R,
): Result<R> = withContext(dispatcher) {
    try {
        action().wrapSuccess()
    } catch (throwable: Throwable) {
        val caughtException = errorMapper.map(throwable) ?: UnknownCaughtException(throwable)
        caughtException.wrapFailure()
    }
}

fun <R> Flow<R>.safeFlow(
    errorMapper: ErrorMapper<CaughtException>,
    dispatcher: CoroutineDispatcher,
): Flow<Result<R>> = map { data ->
    data.wrapSuccess()
}.catch { throwable ->
    val caughtException = errorMapper.map(throwable) ?: UnknownCaughtException(throwable)
    emit(caughtException.wrapFailure())
}.flowOn(dispatcher)


fun <T> T.wrapSuccess() = Result.success(this)

fun <T> CaughtException.wrapFailure() = Result.failure<T>(this)