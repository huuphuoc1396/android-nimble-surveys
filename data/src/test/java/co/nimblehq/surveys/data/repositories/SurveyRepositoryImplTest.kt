package co.nimblehq.surveys.data.repositories

import co.nimblehq.surveys.data.services.AuthApiService
import co.nimblehq.surveys.data.services.responses.survey.Attributes
import co.nimblehq.surveys.data.services.responses.survey.Data
import co.nimblehq.surveys.data.services.responses.survey.Meta
import co.nimblehq.surveys.data.services.responses.survey.SurveyListResponse
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import co.nimblehq.surveys.domain.models.survey.SurveyPageModel
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class SurveyRepositoryImplTest {
    private val authApiService: AuthApiService = mockk()
    private val repositoryImpl = SurveyRepositoryImpl(authApiService)

    @Test
    fun `When get survey list is successful, it returns SurveyPageModel`() =
        runTest {
            coEvery {
                authApiService.getSurveyList(1, 20)
            } returns
                SurveyListResponse(
                    data =
                        listOf(
                            Data(
                                attributes =
                                    Attributes(
                                        activeAt = null,
                                        coverImageUrl = "https://nimbel.hq/cover/1",
                                        createdAt = null,
                                        description = "This is survey description",
                                        inactiveAt = null,
                                        isActive = null,
                                        surveyType = null,
                                        thankEmailAboveThreshold = null,
                                        thankEmailBelowThreshold = null,
                                        title = "Survey",
                                    ),
                                id = "1",
                                type = "hotel",
                            ),
                        ),
                    meta =
                        Meta(
                            page = 1,
                            pageSize = 20,
                            pages = 10,
                            records = 200,
                        ),
                )
            repositoryImpl.getSurveyList(1, 20) shouldBe
                SurveyPageModel(
                    surveyList =
                        listOf(
                            SurveyModel(
                                id = "1",
                                title = "Survey",
                                description = "This is survey description",
                                coverUrl = "https://nimbel.hq/cover/1l",
                            ),
                        ),
                    page = 1,
                    totalPages = 10,
                )
        }

    @Test
    fun `When get survey list is fail, it throw error`() =
        runTest {
            val error = Exception()
            coEvery {
                authApiService.getSurveyList(1, 20)
            } throws error
            try {
                repositoryImpl.getSurveyList(1, 20)
            } catch (e: Exception) {
                e shouldBe error
            }
        }
}
