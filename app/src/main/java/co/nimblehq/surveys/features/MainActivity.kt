package co.nimblehq.surveys.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import co.nimblehq.surveys.features.navigation.SurveysNavHost
import co.nimblehq.surveys.ui.theme.SurveysTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SurveysTheme {
                val systemUiController = rememberSystemUiController()
                val darkIcons = MaterialTheme.colors.isLight
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        Color.Transparent,
                        darkIcons = darkIcons
                    )
                }

                SurveysNavHost()
            }
        }
    }
}