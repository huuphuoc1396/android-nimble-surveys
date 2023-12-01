package co.nimblehq.surveys.features.survey.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import co.nimblehq.surveys.ui.theme.SurveysTheme
import coil.compose.AsyncImage

@Composable
fun SurveyItem(
    modifier: Modifier = Modifier,
    surveyModel: SurveyModel,
    onTakeSurveyClick: (String) -> Unit,
) {
    Box(
        modifier.fillMaxHeight(),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            surveyModel.coverUrl,
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black)
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                },
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Survey(
            modifier = Modifier.padding(20.dp),
            surveyModel = surveyModel,
            onTakeSurveyClick = onTakeSurveyClick,
        )
    }
}

@Preview
@Composable
fun SurveyItemPreview() {
    SurveysTheme {
        SurveyItem(
            surveyModel = SurveyModel(
                id = "1",
                title = "Working from home Check-In",
                description = "We would like to know how you feel about our work from home...",
                coverUrl = "",
            ),
            onTakeSurveyClick = {},
        )
    }
}