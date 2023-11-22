package co.nimblehq.surveys.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import co.nimblehq.surveys.domain.models.user.UserModel
import co.nimblehq.surveys.domain.usecases.EmptyParams
import co.nimblehq.surveys.domain.usecases.auth.GetUserUseCase
import co.nimblehq.surveys.domain.usecases.auth.LogoutUseCase
import co.nimblehq.surveys.domain.usecases.survey.GetSurveyListUseCase
import co.nimblehq.surveys.extensions.launch
import co.nimblehq.surveys.features.home.HomeViewModel.Event
import co.nimblehq.surveys.features.home.HomeViewModel.UiState
import co.nimblehq.surveys.features.survey.list.SurveyPageConfig
import co.nimblehq.surveys.features.survey.list.SurveyPagingSource
import co.nimblehq.surveys.state.UiStateDelegate
import co.nimblehq.surveys.state.UiStateDelegateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getSurveyListUseCase: GetSurveyListUseCase
) : ViewModel(),
    UiStateDelegate<UiState, Event> by UiStateDelegateImpl(UiState()) {

    data class UiState(
        val userModel: UserModel? = null,
        val surveyPagingData: PagingData<SurveyModel>? = null,
    )

    sealed interface Event {
        data object GoToLogin : Event
    }

    init {
        getUser()
        getSurveyList()
    }

    private fun getSurveyList() {
        launch {
            Pager(
                config = PagingConfig(
                    pageSize = SurveyPageConfig.PAGE_SIZE,
                    prefetchDistance = SurveyPageConfig.PAGE_SIZE / 2
                ),
                pagingSourceFactory = {
                    SurveyPagingSource(getSurveyListUseCase)
                },
            )
                .flow
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    reduceAsync(viewModelScope) { uiState ->
                        uiState.copy(surveyPagingData = pagingData)
                    }
                }
        }
    }

    private fun getUser() {
        launch {
            getUserUseCase(EmptyParams).collect { userResult ->
                val userModel = userResult
                    .onFailure { error -> sendError(error) }
                    .getOrNull()
                reduceAsync(viewModelScope) { uiState ->
                    uiState.copy(userModel = userModel)
                }
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