package co.nimblehq.surveys.features.home

import co.nimblehq.surveys.ViewModelTest
import co.nimblehq.surveys.domain.usecases.auth.GetUserUseCase
import co.nimblehq.surveys.domain.usecases.auth.LogoutUseCase
import co.nimblehq.surveys.domain.usecases.survey.GetSurveyListUseCase
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeViewModelTest : ViewModelTest() {

    private val getUserUseCase: GetUserUseCase = mockk()
    private val logoutUseCase: LogoutUseCase = mockk()
    private val getSurveyListUseCase: GetSurveyListUseCase = mockk()

    private lateinit var homeViewModel: HomeViewModel

    override fun setUp() {
        super.setUp()
        homeViewModel = HomeViewModel(
            getUserUseCase = getUserUseCase,
            logoutUseCase = logoutUseCase,
            getSurveyListUseCase = getSurveyListUseCase,
        )
    }
}