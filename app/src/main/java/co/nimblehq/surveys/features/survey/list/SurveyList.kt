package co.nimblehq.surveys.features.survey.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import co.nimblehq.surveys.features.components.Loading
import co.nimblehq.surveys.features.error.userReadableMessage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurveyList(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<SurveyModel>,
    state: LazyListState = rememberLazyListState(),
) {
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
    BoxWithConstraints {
        LazyRow(
            modifier = modifier,
            state = state,
            flingBehavior = flingBehavior,
        ) {
            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let { surveyModel ->
                    SurveyItem(
                        Modifier.size(maxWidth, maxHeight),
                        surveyModel = surveyModel,
                    )
                }
            }

            when {
                pagingItems.loadState.refresh is LoadState.Loading -> {
                    item { Loading(Modifier.size(maxWidth, maxHeight)) }
                }

                pagingItems.loadState.refresh is LoadState.Error -> {
                    val error = (pagingItems.loadState.refresh as LoadState.Error).error
                    item {
                        SurveyPageErrorMessage(
                            Modifier.size(maxWidth, maxHeight),
                            message = error.userReadableMessage(LocalContext.current),
                            onRetryClick = { pagingItems.retry() },
                        )
                    }
                }

                pagingItems.loadState.append is LoadState.Loading -> {
                    item { Loading(Modifier.size(maxWidth, maxHeight)) }
                }

                pagingItems.loadState.append is LoadState.Error -> {
                    val error = (pagingItems.loadState.append as LoadState.Error).error
                    item {
                        SurveyPageErrorMessage(
                            Modifier.size(maxWidth, maxHeight),
                            message = error.userReadableMessage(LocalContext.current),
                            onRetryClick = { pagingItems.retry() },
                        )
                    }
                }
            }
        }
    }
}