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
        val isLoading: Boolean = false,
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
        asyncUpdate(viewModelScope) { state -> state.copy(email = email) }
    }

    fun onPasswordChanged(password: String) {
        asyncUpdate(viewModelScope) { state -> state.copy(password = password) }
    }

    fun onLoginClick() {
        launch {
            update { state -> state.copy(isLoading = true) }
            val loginParams = LoginUseCase.Params(
                email = uiState.email,
                password = uiState.password,
            )
            val isLoggedIn = loginUseCase(loginParams).getOrNull()
            if (isLoggedIn == true) {
                sendEvent(Event.GoToHome)
            }
        }.invokeOnCompletion {
            asyncUpdate(viewModelScope) { state -> state.copy(isLoading = false) }
        }
    }

    fun onForgotClick() {
        launch { sendEvent(Event.GoToForgotPassword) }
    }
}
