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
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val getUserUseCase: GetUserUseCase,
        private val logoutUseCase: LogoutUseCase,
        private val getSurveyListUseCase: GetSurveyListUseCase,
    ) : ViewModel(),
        UiStateDelegate<UiState, Event> by UiStateDelegateImpl(UiState()) {
        data class UiState(
            val userModel: UserModel? = null,
            val surveyPagingData: Flow<PagingData<SurveyModel>>? = null,
        )

        sealed interface Event {
            data object GoToLogin : Event

            data class GoToSurveyDetail(val id: String) : Event
        }

        init {
            getUser()
            getSurveyList()
        }

        fun getSurveyList() {
            launch {
                val pagingData =
                    Pager(
                        config =
                            PagingConfig(
                                pageSize = SurveyPageConfig.PAGE_SIZE,
                                prefetchDistance = 1,
                            ),
                        pagingSourceFactory = {
                            SurveyPagingSource(getSurveyListUseCase)
                        },
                    )
                        .flow
                        .cachedIn(viewModelScope)
                reduce { uiState ->
                    uiState.copy(surveyPagingData = pagingData)
                }
            }
        }

        fun getUser() {
            launch {
                getUserUseCase(Unit).collect { userResult ->
                    val userModel =
                        userResult
                            .onFailure { error -> sendError(error) }
                            .getOrNull()
                    reduce { uiState ->
                        uiState.copy(userModel = userModel)
                    }
                }
            }
        }

        fun onLogoutClick() {
            launch(loading = this) {
                logoutUseCase(EmptyParams)
                    .onFailure { error -> sendError(error) }
                    .onSuccess {
                        sendEvent(Event.GoToLogin)
                    }
            }
        }

        fun onTakeSurveyClick(id: String) {
            launch {
                sendEvent(Event.GoToSurveyDetail(id))
            }
        }
    }
