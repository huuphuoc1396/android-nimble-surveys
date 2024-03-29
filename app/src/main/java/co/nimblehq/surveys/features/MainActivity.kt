package co.nimblehq.surveys.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import co.nimblehq.surveys.features.navigation.AppNavHost
import co.nimblehq.surveys.ui.theme.SurveysTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SurveysTheme {
                rememberSystemUiController().setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = false,
                )
                AppNavHost()
            }
        }
    }
}