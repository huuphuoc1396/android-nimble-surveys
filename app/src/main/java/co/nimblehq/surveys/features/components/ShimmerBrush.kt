package co.nimblehq.surveys.features.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun shimmerBrush(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 0f,
    durationMillis: Int = 2000,
): Brush {

    val shimmerColors = listOf(
        Color.White.copy(alpha = 0.12f),
        Color.White.copy(alpha = 0.24f),
        Color.White.copy(alpha = 0.48f),
        Color.White.copy(alpha = 0.24f),
        Color.White.copy(alpha = 0.12f),
    )

    val transition = rememberInfiniteTransition(label = "Shimmer Loading")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "Shimmer Loading",
    )

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
        end = Offset(x = translateAnimation.value, y = angleOfAxisY),
    )
}