package co.nimblehq.surveys.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val themeColors = lightColors(

)

@Composable
fun SurveysTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = themeColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
