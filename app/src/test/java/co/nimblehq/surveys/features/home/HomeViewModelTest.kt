package co.nimblehq.surveys.features.home

import app.cash.turbine.test
import co.nimblehq.surveys.ViewModelTest
import co.nimblehq.surveys.domain.functional.wrapFailure
import co.nimblehq.surveys.domain.functional.wrapSuccess
import co.nimblehq.surveys.domain.models.survey.SurveyPageModel
import co.nimblehq.surveys.domain.models.user.UserModel
import co.nimblehq.surveys.domain.usecases.EmptyParams
import co.nimblehq.surveys.domain.usecases.auth.GetUserUseCase
import co.nimblehq.surveys.domain.usecases.auth.LogoutUseCase
import co.nimblehq.surveys.domain.usecases.survey.GetSurveyListUseCase
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest : ViewModelTest() {
    private val getUserUseCase: GetUserUseCase = mockk()
    private val logoutUseCase: LogoutUseCase = mockk()
    private val getSurveyListUseCase: GetSurveyListUseCase = mockk()
    private lateinit var homeViewModel: HomeViewModel
    private val userModel =
        UserModel(
            id = "1",
            name = "Tester",
            email = "tester@mail.com",
            avatarUrl = "https://nimble.hq/avatar/1",
        )

    override fun setUp() {
        super.setUp()
        coEvery { getUserUseCase(Unit) } returns flowOf(userModel.wrapSuccess())
        coEvery {
            val params = GetSurveyListUseCase.Params(1, 20)
            getSurveyListUseCase(params)
        } returns SurveyPageModel(listOf(), 0, 0).wrapSuccess()
        homeViewModel =
            HomeViewModel(
                getUserUseCase = getUserUseCase,
                logoutUseCase = logoutUseCase,
                getSurveyListUseCase = getSurveyListUseCase,
            )
    }

    @Test
    fun `When call get survey list, it creates a paging data and updates to the UiState `() =
        runTest {
            homeViewModel.getSurveyList()
            homeViewModel.uiStateFlow.test {
                expectMostRecentItem().surveyPagingData shouldNotBe null
            }
        }

    @Test
    fun `When get user is success, it updates UserModel in the UiState`() =
        runTest {
            coEvery { getUserUseCase(Unit) } returns flowOf(userModel.wrapSuccess())
            homeViewModel.getUser()
            homeViewModel.uiStateFlow.test {
                expectMostRecentItem().userModel shouldBe userModel
            }
        }

    @Test
    fun `When get user is error, it emits an error`() =
        runTest {
            val error = Throwable()
            coEvery { getUserUseCase(Unit) } returns flowOf(error.wrapFailure())
            homeViewModel.getUser()
            homeViewModel.error.test {
                expectMostRecentItem() shouldBe error
            }
        }

    @Test
    fun `When take a surveys clicks, it send a GoToSurveyDetail event`() =
        runTest {
            homeViewModel.onTakeSurveyClick("123")
            homeViewModel.singleEvents.test {
                expectMostRecentItem() shouldBe HomeViewModel.Event.GoToSurveyDetail("123")
            }
        }

    @Test
    fun `When logout is successfully, it navigate to the login`() =
        runTest {
            coEvery { logoutUseCase(EmptyParams) } returns Unit.wrapSuccess()
            homeViewModel.onLogoutClick()
            homeViewModel.singleEvents.test {
                expectMostRecentItem() shouldBe HomeViewModel.Event.GoToLogin
            }
        }

    @Test
    fun `When logout is error, it throws an error`() =
        runTest {
            val error = Throwable()
            coEvery { logoutUseCase(EmptyParams) } returns error.wrapFailure()
            homeViewModel.onLogoutClick()
            homeViewModel.error.test {
                expectMostRecentItem() shouldBe error
            }
        }
}
