package co.nimblehq.surveys.data.services.responses.user

import co.nimblehq.surveys.data.services.responses.survey.Attributes
import co.nimblehq.surveys.data.services.responses.survey.Data
import co.nimblehq.surveys.data.services.responses.survey.Meta
import co.nimblehq.surveys.data.services.responses.survey.SurveyListResponse
import co.nimblehq.surveys.data.services.responses.survey.toSurveyPageModel
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import co.nimblehq.surveys.domain.models.survey.SurveyPageModel
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SurveyListResponseMapperTest {

    @Test
    fun `map SurveyListResponse to SurveyPageModel`() {
        val surveyListResponse = SurveyListResponse(
            data = listOf(
                Data(
                    attributes = Attributes(
                        activeAt = null,
                        coverImageUrl = "https://nimbel.hq/cover/1",
                        createdAt = null,
                        description = "This is survey description",
                        inactiveAt = null,
                        isActive = null,
                        surveyType = null,
                        thankEmailAboveThreshold = null,
                        thankEmailBelowThreshold = null,
                        title = "Survey"
                    ),
                    id = "1",
                    type = "hotel",
                )
            ),
            meta = Meta(
                page = 1,
                pageSize = 20,
                pages = 10,
                records = 200,
            )
        )
        surveyListResponse.toSurveyPageModel() shouldBe SurveyPageModel(
            surveyList = listOf(
                SurveyModel(
                    id = "1",
                    title = "Survey",
                    description = "This is survey description",
                    coverUrl = "https://nimbel.hq/cover/1l",
                )
            ),
            page = 1,
            totalPages = 10,
        )
    }
}