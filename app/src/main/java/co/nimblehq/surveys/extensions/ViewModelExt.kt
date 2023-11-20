package co.nimblehq.surveys.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import co.nimblehq.surveys.state.UiStateDelegate as Loading


fun ViewModel.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    loading: Loading<*, *>? = null,
    block: suspend CoroutineScope.() -> Unit
): Job {
    loading?.showLoading()
    return viewModelScope
        .launch(
            context = context,
            start = start,
            block = block,
        )
        .also { job ->
            job.invokeOnCompletion {
                loading?.hideLoading()
            }
        }
}

fun <T> Flow<T>.injectLoading(
    loading: Loading<*, *>,
): Flow<T> = this
    .onStart { loading.showLoading() }
    .onCompletion { loading.hideLoading() }