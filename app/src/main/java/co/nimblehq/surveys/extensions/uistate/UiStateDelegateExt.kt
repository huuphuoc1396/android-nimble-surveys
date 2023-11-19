package co.nimblehq.surveys.extensions.uistate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.nimblehq.surveys.state.UiStateDelegate

@Composable
fun <R> UiStateDelegate<R, *>.collectUiState() = this.uiStateFlow.collectAsState()

@Composable
fun <R> UiStateDelegate<R, *>.collectWithLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) = this.uiStateFlow.collectAsStateWithLifecycle(
    minActiveState = minActiveState,
)
