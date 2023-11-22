package co.nimblehq.surveys.domain.usecases.survey

import co.nimblehq.surveys.domain.errors.exceptions.network.NetworkCaughtException
import co.nimblehq.surveys.domain.errors.mappers.remote.RemoteErrorMapper
import co.nimblehq.surveys.domain.models.survey.SurveyPageModel
import co.nimblehq.surveys.domain.repositories.SurveyRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class GetSurveyListUseCaseTest {

    private val surveyRepository = mockk<SurveyRepository>()
    private val remoteErrorMapper = mockk<RemoteErrorMapper>()
    private val getSurveyListUseCase = GetSurveyListUseCase(
        surveyRepository = surveyRepository,
        dispatcher = UnconfinedTestDispatcher(),
        remoteErrorMapper = remoteErrorMapper
    )

    @Test
    fun `When get survey list is successful, it returns SurveyPageModel`() = runTest {
        val surveyPageModel = SurveyPageModel(
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
    fun `When get survey list is fail, it throw Server error`() = runTest {
        val error = Exception()
        coEvery { surveyRepository.getSurveyList(1, 10) } throws error

        val serverError = NetworkCaughtException.Server(401, "")
        every { remoteErrorMapper.map(error) } returns serverError

        val params = GetSurveyListUseCase.Params(1, 10)
        val result = getSurveyListUseCase(params)
        result.exceptionOrNull() shouldBe serverError
    }
}