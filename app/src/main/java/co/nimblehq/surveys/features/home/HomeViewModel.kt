package co.nimblehq.surveys.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nimblehq.surveys.domain.models.UserModel
import co.nimblehq.surveys.domain.usecases.EmptyParams
import co.nimblehq.surveys.domain.usecases.auth.GetUserUseCase
import co.nimblehq.surveys.domain.usecases.auth.LogoutUseCase
import co.nimblehq.surveys.extensions.launch
import co.nimblehq.surveys.features.home.HomeViewModel.Event
import co.nimblehq.surveys.features.home.HomeViewModel.UiState
import co.nimblehq.surveys.state.UiStateDelegate
import co.nimblehq.surveys.state.UiStateDelegateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel(),
    UiStateDelegate<UiState, Event> by UiStateDelegateImpl(UiState()) {

    data class UiState(
        val userModel: UserModel? = null
    )

    sealed interface Event {
        data object GoToLogin : Event
    }

    init {
        getUser()
    }

    private fun getUser() {
        launch(loading = this) {
            val userModel = getUserUseCase(EmptyParams)
                .onFailure { error -> sendError(error) }
                .getOrNull()
            reduceAsync(viewModelScope) { uiState ->
                uiState.copy(userModel = userModel)
            }
        }
    }

    fun onLogoutClick() {
        launch(loading = this) {
            logoutUseCase(EmptyParams)
                .onFailure { error -> sendError(error) }
            sendEvent(Event.GoToLogin)
        }
    }
}