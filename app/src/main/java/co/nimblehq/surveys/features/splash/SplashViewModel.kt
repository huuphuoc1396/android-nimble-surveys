package co.nimblehq.surveys.features.splash

import androidx.lifecycle.ViewModel
import co.nimblehq.surveys.extensions.launch
import co.nimblehq.surveys.features.splash.SplashViewModel.Event
import co.nimblehq.surveys.features.splash.SplashViewModel.UiState
import co.nimblehq.surveys.state.UiStateDelegate
import co.nimblehq.surveys.state.UiStateDelegateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() :
    ViewModel(),
    UiStateDelegate<UiState, Event> by UiStateDelegateImpl(UiState) {

    companion object {
        private const val SPLASH_DELAY = 3000L
    }

    data object UiState

    sealed interface Event {
        data object GoToHome : Event
        data object GoToLogin : Event
    }

    init {
        initSplash()
    }

    private fun initSplash() {
        launch {
            delay(SPLASH_DELAY)
            sendEvent(Event.GoToLogin)
        }
    }
}