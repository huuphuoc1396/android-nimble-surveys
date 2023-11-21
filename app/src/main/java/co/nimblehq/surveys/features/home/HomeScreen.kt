package co.nimblehq.surveys.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.nimblehq.surveys.extensions.collectErrorEffect
import co.nimblehq.surveys.extensions.collectEventEffect
import co.nimblehq.surveys.extensions.collectLoadingWithLifecycle
import co.nimblehq.surveys.extensions.collectUiStateWithLifecycle
import co.nimblehq.surveys.features.components.LoadingContent
import co.nimblehq.surveys.features.error.showToast
import co.nimblehq.surveys.features.navigation.AppDestination
import co.nimblehq.surveys.features.navigation.navigate
import co.nimblehq.surveys.ui.theme.SurveysTheme
import kotlinx.coroutines.launch

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

@Composable
private fun HomeContent(
    uiState: HomeViewModel.UiState,
    onLogoutClick: () -> Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    HomeDrawer(
        drawerState = drawerState,
        drawerContent = {
            HomeDrawerContent(
                name = uiState.userModel?.name,
                avatarUrl = uiState.userModel?.avatarUrl,
                onLogoutClick = {
                    scope.launch { drawerState.close() }
                    onLogoutClick()
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
        ) {
            HomeTopBar(
                avatarUrl = uiState.userModel?.avatarUrl,
                onAccountClick = {
                    scope.launch { drawerState.open() }
                },
            )
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    SurveysTheme {
        HomeScreen()
    }
}