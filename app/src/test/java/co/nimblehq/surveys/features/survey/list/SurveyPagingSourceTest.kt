package co.nimblehq.surveys.features.survey.list

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import co.nimblehq.surveys.domain.functional.wrapFailure
import co.nimblehq.surveys.domain.functional.wrapSuccess
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import co.nimblehq.surveys.domain.models.survey.SurveyPageModel
import co.nimblehq.surveys.domain.usecases.survey.GetSurveyListUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class SurveyPagingSourceTest {
    private val getSurveyListUseCase: GetSurveyListUseCase = mockk()
    private val pagingSource = SurveyPagingSource(getSurveyListUseCase)

    @Test
    fun `When loads a single page successfully, it returns survey list `() =
        runTest {
            val surveyList =
                List(10) { index ->
                    SurveyModel(
                        id = "$index",
                        title = "Survey title $index",
                        description = "Survey description $index",
                        coverUrl = "https://nimbel.hq/cover/1",
                    )
                }
            coEvery {
                val params = GetSurveyListUseCase.Params(1, SurveyPageConfig.PAGE_SIZE)
                getSurveyListUseCase(params)
            } returns SurveyPageModel(surveyList, 1, 1).wrapSuccess()

            val pager =
                TestPager(
                    config =
                        PagingConfig(
                            pageSize = SurveyPageConfig.PAGE_SIZE,
                            prefetchDistance = SurveyPageConfig.PAGE_SIZE / 2,
                        ),
                    pagingSource = pagingSource,
                )
            val result = pager.refresh() as PagingSource.LoadResult.Page
            result.data shouldBe surveyList
        }

    @Test
    fun `When loads consecutive pages successfully, it returns survey list by page`() =
        runTest {
            val surveyList1 =
                List(10) { index ->
                    SurveyModel(
                        id = "$index",
                        title = "Survey title $index",
                        description = "Survey description $index",
                        coverUrl = "https://nimbel.hq/cover/1",
                    )
                }
            val surveyList2 =
                List(5) { index ->
                    SurveyModel(
                        id = "$index",
                        title = "Survey title $index",
                        description = "Survey description $index",
                        coverUrl = "https://nimbel.hq/cover/1",
                    )
                }
            coEvery {
                val params = GetSurveyListUseCase.Params(1, SurveyPageConfig.PAGE_SIZE)
                getSurveyListUseCase(params)
            } returns SurveyPageModel(surveyList1, 1, 2).wrapSuccess()

            coEvery {
                val params = GetSurveyListUseCase.Params(2, SurveyPageConfig.PAGE_SIZE)
                getSurveyListUseCase(params)
            } returns SurveyPageModel(surveyList2, 2, 2).wrapSuccess()

            val pager =
                TestPager(
                    config =
                        PagingConfig(
                            pageSize = SurveyPageConfig.PAGE_SIZE,
                            prefetchDistance = SurveyPageConfig.PAGE_SIZE / 2,
                        ),
                    pagingSource = pagingSource,
                )
            val result1 =
                with(pager) {
                    refresh()
                } as PagingSource.LoadResult.Page

            result1.data shouldBe surveyList1

            val result2 =
                with(pager) {
                    append()
                } as PagingSource.LoadResult.Page

            result2.data shouldBe surveyList2
        }

    @Test
    fun `When refresh a page is fail, it returns a page error `() =
        runTest {
            val error = Throwable()
            coEvery {
                val params = GetSurveyListUseCase.Params(1, SurveyPageConfig.PAGE_SIZE)
                getSurveyListUseCase(params)
            } returns error.wrapFailure()

            val pager =
                TestPager(
                    config =
                        PagingConfig(
                            pageSize = SurveyPageConfig.PAGE_SIZE,
                            prefetchDistance = SurveyPageConfig.PAGE_SIZE / 2,
                        ),
                    pagingSource = pagingSource,
                )
            val result = pager.refresh() as PagingSource.LoadResult.Error
            result shouldBe PagingSource.LoadResult.Error(error)
        }
}
