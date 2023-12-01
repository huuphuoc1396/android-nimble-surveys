package co.nimblehq.surveys.features.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import co.nimblehq.surveys.extensions.collectErrorEffect
import co.nimblehq.surveys.extensions.collectEventEffect
import co.nimblehq.surveys.extensions.collectLoadingWithLifecycle
import co.nimblehq.surveys.extensions.collectUiStateWithLifecycle
import co.nimblehq.surveys.features.components.LoadingContent
import co.nimblehq.surveys.features.error.showToast
import co.nimblehq.surveys.features.navigation.AppDestination
import co.nimblehq.surveys.features.navigation.navigate
import co.nimblehq.surveys.features.survey.list.SurveyList
import co.nimblehq.surveys.ui.theme.SurveysTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel(),
) {

    viewModel.collectEventEffect { event ->
        when (event) {
            HomeViewModel.Event.GoToLogin -> {
                navController.navigate(AppDestination.Login) {
                    popUpTo(AppDestination.Home.route) { inclusive = true }
                }
            }

            is HomeViewModel.Event.GoToSurveyDetail -> {
                navController.navigate(AppDestination.SurveyDetail.createRoute(id = event.id))
            }
        }
    }

    val context = LocalContext.current
    viewModel.collectErrorEffect { throwable ->
        throwable.showToast(context)
    }

    val isLoading by viewModel.collectLoadingWithLifecycle()
    LoadingContent(isLoading) {
        val uiState by viewModel.collectUiStateWithLifecycle()
        HomeContent(
            uiState,
            onLogoutClick = viewModel::onLogoutClick,
            onTakeSurveyClick = viewModel::onTakeSurveyClick,
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
private fun HomeContent(
    uiState: HomeViewModel.UiState,
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit = {},
    onTakeSurveyClick: (String) -> Unit = {},
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    HomeDrawer(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures {
                coroutineScope.launch { drawerState.close() }
            }
        },
        drawerState = drawerState,
        drawerContent = {
            HomeDrawerContent(
                name = uiState.userModel?.name,
                avatarUrl = uiState.userModel?.avatarUrl,
                onLogoutClick = {
                    onLogoutClick()
                    coroutineScope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            uiState.surveyPagingData?.let { pagingData ->
                val listState = rememberLazyListState()
                val pagingItems = pagingData.collectAsLazyPagingItems()
                SurveyList(
                    pagingItems = pagingItems,
                    onTakeSurveyClick = onTakeSurveyClick,
                    listState = listState,
                )

                val isRefreshing = pagingItems.loadState.refresh is LoadState.Loading
                val pullRefreshState = rememberPullRefreshState(
                    refreshing = isRefreshing,
                    onRefresh = {
                        coroutineScope.launch {
                            listState.scrollToItem(0)
                            pagingItems.refresh()
                        }
                    },
                )
                HomeTopBar(
                    modifier = Modifier
                        .statusBarsPadding()
                        .pullRefresh(pullRefreshState)
                        .verticalScroll(rememberScrollState()),
                    avatarUrl = uiState.userModel?.avatarUrl,
                    onAccountClick = {
                        coroutineScope.launch { drawerState.open() }
                    },
                )

                AnimatedVisibility(
                    visible = isRefreshing,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    HomeShimmerLoading()
                }

                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    contentColor = Color.DarkGray
                )
            }
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview
@Composable
fun HomeContentPreview() {
    SurveysTheme {
        HomeContent(HomeViewModel.UiState())
    }
}