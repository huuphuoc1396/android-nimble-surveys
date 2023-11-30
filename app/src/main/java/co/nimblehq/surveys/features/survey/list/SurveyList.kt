package co.nimblehq.surveys.features.survey.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onTakeSurveyClick: (String) -> Unit,
) {
    val listState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(listState)
    val selectedIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.BottomStart,
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            flingBehavior = flingBehavior,
        ) {
            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let { surveyModel ->
                    SurveyItem(
                        Modifier.size(maxWidth, maxHeight),
                        surveyModel = surveyModel,
                        onTakeSurveyClick = onTakeSurveyClick,
                    )
                }
            }

            when {
                pagingItems.loadState.refresh is LoadState.Loading -> {}

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
        Box(modifier = Modifier.navigationBarsPadding()) {
            DotsIndicator(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 152.dp),
                totalDots = firstSurveyModel?.totalRecords.defaultZero(),
                selectedIndex = selectedIndex,
            )
        }
    }
}