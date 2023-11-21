package co.nimblehq.surveys.features.survey.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.surveys.R
import co.nimblehq.surveys.domain.models.survey.SurveyModel
import co.nimblehq.surveys.ui.theme.SurveysTheme

@Composable
fun Survey(
    modifier: Modifier = Modifier,
    surveyModel: SurveyModel,
) {
    Column(modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = surveyModel.title,
            style = MaterialTheme.typography.h5,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f).padding(top = 14.dp),
                text = surveyModel.description,
                style = MaterialTheme.typography.body1.copy(
                    color = Color.White.copy(alpha = 0.7f)
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(20.dp))
            IconButton(
                modifier = Modifier
                    .size(56.dp, 56.dp)
                    .padding(top = 2.dp)
                    .background(Color.White, CircleShape),
                onClick = { /*TODO*/ },
            ) {
                Image(
                    modifier = Modifier.size(28.dp, 30.dp),
                    painter = painterResource(id = R.drawable.ic_action_next),
                    contentDescription = null,
                )
            }
        }
        Spacer(modifier = Modifier.navigationBarsPadding())
    }
}

@Preview
@Composable
fun SurveyPreview() {
    SurveysTheme {
        Survey(
            surveyModel = SurveyModel(
                id = "1",
                title = "Working from home Check-In",
                description = "We would like to know how you feel about our work from home...",
                coverUrl = "",
            )
        )
    }
}

