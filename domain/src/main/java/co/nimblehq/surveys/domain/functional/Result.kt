package co.nimblehq.surveys.domain.functional

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

suspend fun <R> safeSuspend(
    dispatcher: CoroutineDispatcher,
    action: suspend () -> R,
): Result<R> = withContext(dispatcher) {
    try {
        action().wrapSuccess()
    } catch (throwable: Throwable) {
        throwable.wrapFailure()
    }
}

fun <R> Flow<R>.safeFlow(
    dispatcher: CoroutineDispatcher,
): Flow<Result<R>> = map { data ->
    data.wrapSuccess()
}.catch { throwable ->
    emit(throwable.wrapFailure())
}.flowOn(dispatcher)


fun <T> T.wrapSuccess() = Result.success(this)

fun <T> Throwable.wrapFailure() = Result.failure<T>(this)