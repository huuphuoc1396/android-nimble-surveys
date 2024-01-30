package co.nimblehq.surveys.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import co.nimblehq.surveys.R

private val themeColors =
    lightColors(
        primary = Color.White,
    )

@Composable
fun SurveysTheme(content: @Composable () -> Unit) {
    Box(
        modifier =
            Modifier.paint(
                painter = painterResource(id = R.drawable.bg_window),
                contentScale = ContentScale.Crop,
            ),
    ) {
        MaterialTheme(
            colors = themeColors,
            typography = typography,
            shapes = shapes,
            content = content,
        )
    }
}
