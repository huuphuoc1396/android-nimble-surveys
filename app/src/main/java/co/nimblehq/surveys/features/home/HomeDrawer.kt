package co.nimblehq.surveys.features.home

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun HomeDrawer(
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        ModalDrawer(
            drawerState = drawerState,
            modifier = modifier,
            drawerContent = {
                CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Ltr,
                ) {
                    drawerContent()
                }
            },
            drawerBackgroundColor = Color.Black.copy(alpha = 0f),
            drawerElevation = 0.dp
        ) {
            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Ltr,
            ) {
                content()
            }
        }
    }
}