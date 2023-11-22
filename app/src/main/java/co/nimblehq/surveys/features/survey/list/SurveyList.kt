package co.nimblehq.surveys.features.survey.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import co.nimblehq.surveys.domain.extensions.defaultZero
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import co.nimblehq.surveys.features.components.DotsIndicator
import co.nimblehq.surveys.features.components.Loading
import co.nimblehq.surveys.features.error.userReadableMessage

@ExperimentalFoundationApi
@Composable
fun SurveyList(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<SurveyModel>,
    state: LazyListState = rememberLazyListState(),
) {
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
    val selectedIndex by remember { derivedStateOf { state.firstVisibleItemIndex } }
    BoxWithConstraints(
        contentAlignment = Alignment.BottomStart,
    ) {
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

        val firstSurveyModel = pagingItems.itemSnapshotList.firstOrNull()
        DotsIndicator(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 172.dp),
            totalDots = firstSurveyModel?.totalRecords.defaultZero(),
            selectedIndex = selectedIndex,
        )
    }
}