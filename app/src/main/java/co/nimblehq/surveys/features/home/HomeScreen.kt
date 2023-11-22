package co.nimblehq.surveys.features.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
                SurveyList(
                    modifier = Modifier.fillMaxSize(),
                    pagingItems = pagingData.collectAsLazyPagingItems(),
                    onTakeSurveyClick = onTakeSurveyClick,
                )
            }

            HomeTopBar(
                modifier = Modifier.statusBarsPadding(),
                avatarUrl = uiState.userModel?.avatarUrl,
                onAccountClick = {
                    coroutineScope.launch { drawerState.open() }
                },
            )
        }
    }
}


@ExperimentalFoundationApi
@Preview
@Composable
fun HomeContentPreview() {
    SurveysTheme {
        HomeContent(HomeViewModel.UiState())
    }
}