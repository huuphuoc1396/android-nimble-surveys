package co.nimblehq.surveys.features.survey.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.surveys.R
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import co.nimblehq.surveys.ui.theme.SurveysTheme
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun SurveyItem(
    modifier: Modifier = Modifier,
    surveyModel: SurveyModel,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomStart,
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(surveyModel.coverUrl)
                    .placeholder(R.drawable.bg_overlay)
                    .build()
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Survey(
            modifier.padding(20.dp),
            surveyModel = surveyModel,
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
            )
        )
    }
}