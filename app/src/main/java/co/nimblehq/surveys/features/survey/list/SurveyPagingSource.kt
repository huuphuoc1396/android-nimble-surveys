package co.nimblehq.surveys.features.survey.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.nimblehq.surveys.domain.extensions.default
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import co.nimblehq.surveys.domain.usecases.survey.GetSurveyListUseCase

class SurveyPagingSource constructor(
    private val getSurveyListUseCase: GetSurveyListUseCase
) : PagingSource<Int, SurveyModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SurveyModel> {
        val currentPage = params.key.default(1)
        val surveyPageResult = getSurveyListUseCase(
            params = GetSurveyListUseCase.Params(currentPage, SurveyPageConfig.PAGE_SIZE)
        )
        surveyPageResult.fold(
            onSuccess = { surveyPage ->
                return LoadResult.Page(
                    data = surveyPage.surveyList,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (currentPage > surveyPage.totalPages) null else currentPage + 1
                )
            },
            onFailure = { throwable ->
                return LoadResult.Error(throwable)
            },
        )
    }


    override fun getRefreshKey(state: PagingState<Int, SurveyModel>): Int? {
        return state.anchorPosition
    }

}