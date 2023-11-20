package co.nimblehq.surveys.features.splash

import androidx.lifecycle.ViewModel
import co.nimblehq.surveys.domain.usecases.EmptyParams
import co.nimblehq.surveys.domain.usecases.auth.GetLoggedInUseCase
import co.nimblehq.surveys.extensions.launch
import co.nimblehq.surveys.features.splash.SplashViewModel.Event
import co.nimblehq.surveys.features.splash.SplashViewModel.UiState
import co.nimblehq.surveys.state.UiStateDelegate
import co.nimblehq.surveys.state.UiStateDelegateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLoggedInUseCase: GetLoggedInUseCase,
) : ViewModel(),
    UiStateDelegate<UiState, Event> by UiStateDelegateImpl(UiState) {

    companion object {
        private const val SPLASH_DELAY = 1500L
    }

    data object UiState

    sealed interface Event {
        data object GoToHome : Event
        data object GoToLogin : Event
    }

    init {
        checkLoggedIn()
    }

    private fun checkLoggedIn() {
        launch {
            delay(SPLASH_DELAY)
            getLoggedInUseCase(EmptyParams).collect { isLoggedInResult ->
                val isLoggedIn = isLoggedInResult
                    .onFailure { error -> sendError(error) }
                    .getOrNull()
                if (isLoggedIn == true) {
                    sendEvent(Event.GoToHome)
                } else {
                    sendEvent(Event.GoToLogin)
                }
            }
        }
    }
}