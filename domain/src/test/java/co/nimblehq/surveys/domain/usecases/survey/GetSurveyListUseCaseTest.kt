package co.nimblehq.surveys.domain.usecases.survey

import co.nimblehq.surveys.domain.models.survey.SurveyPageModel
import co.nimblehq.surveys.domain.repositories.SurveyRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetSurveyListUseCaseTest {
    private val surveyRepository = mockk<SurveyRepository>()
    private val getSurveyListUseCase =
        GetSurveyListUseCase(
            surveyRepository = surveyRepository,
            dispatcher = UnconfinedTestDispatcher(),
        )

    @Test
    fun `When get survey list is successful, it returns SurveyPageModel`() =
        runTest {
            val surveyPageModel =
                SurveyPageModel(
                    surveyList = listOf(),
                    page = 1,
                    totalPages = 1,
                )
            coEvery { surveyRepository.getSurveyList(1, 10) } returns surveyPageModel
            val params = GetSurveyListUseCase.Params(1, 10)
            val result = getSurveyListUseCase(params)
            result.getOrNull() shouldBe surveyPageModel
        }

    @Test
    fun `When get survey list is fail, it throw error`() =
        runTest {
            val error = Exception()
            coEvery { surveyRepository.getSurveyList(1, 10) } throws error

            val params = GetSurveyListUseCase.Params(1, 10)
            val result = getSurveyListUseCase(params)
            result.exceptionOrNull() shouldBe error
        }
}
