package co.nimblehq.surveys.features.home

import androidx.compose.foundation.ExperimentalFoundationApi
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
import kotlinx.coroutines.flow.flowOf
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
        }
    }

    val context = LocalContext.current
    viewModel.collectErrorEffect { throwable ->
        throwable.showToast(context)
    }

    val isLoading by viewModel.collectLoadingWithLifecycle()
    LoadingContent(isLoading) {
        val uiState by viewModel.collectUiStateWithLifecycle()
        HomeContent(uiState, onLogoutClick = viewModel::onLogoutClick)
    }
}

@ExperimentalFoundationApi
@Composable
private fun HomeContent(
    uiState: HomeViewModel.UiState,
    onLogoutClick: () -> Unit = {},
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    HomeDrawer(
        drawerState = drawerState,
        drawerContent = {
            HomeDrawerContent(
                name = uiState.userModel?.name,
                avatarUrl = uiState.userModel?.avatarUrl,
                onLogoutClick = {
                    onLogoutClick()
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            uiState.surveyPagingData?.let { pagingData ->
                SurveyList(
                    modifier = Modifier.fillMaxSize(),
                    pagingItems = flowOf(pagingData).collectAsLazyPagingItems(),
                )
            }

            HomeTopBar(
                modifier = Modifier.statusBarsPadding(),
                avatarUrl = uiState.userModel?.avatarUrl,
                onAccountClick = {
                    scope.launch { drawerState.open() }
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