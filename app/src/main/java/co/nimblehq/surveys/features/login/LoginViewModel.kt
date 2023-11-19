package co.nimblehq.surveys.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nimblehq.surveys.domain.usecases.auth.LoginUseCase
import co.nimblehq.surveys.extensions.launch
import co.nimblehq.surveys.features.login.LoginViewModel.Event
import co.nimblehq.surveys.features.login.LoginViewModel.UiState
import co.nimblehq.surveys.state.UiStateDelegate
import co.nimblehq.surveys.state.UiStateDelegateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel(),
    UiStateDelegate<UiState, Event> by UiStateDelegateImpl(UiState()) {

    data class UiState(
        val email: String = "",
        val password: String = "",
    ) {
        val isLoginEnabled: Boolean
            get() = email.isNotBlank() && password.isNotBlank()
    }

    sealed interface Event {
        data object GoToHome : Event

        data object GoToForgotPassword : Event
    }

    fun onEmailChanged(email: String) {
        reduceAsync(viewModelScope) { state -> state.copy(email = email) }
    }

    fun onPasswordChanged(password: String) {
        reduceAsync(viewModelScope) { state -> state.copy(password = password) }
    }

    fun onLoginClick() {
        launch(loading = this) {
            val loginParams = LoginUseCase.Params(
                email = uiState.email,
                password = uiState.password,
            )
            val isLoggedIn = loginUseCase(loginParams)
                .onFailure { error -> sendError(error) }
                .getOrNull()
            if (isLoggedIn == true) {
                sendEvent(Event.GoToHome)
            }
        }
    }

    fun onForgotClick() {
        launch {
            sendEvent(Event.GoToForgotPassword)
        }
    }
}
