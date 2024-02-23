package co.nimblehq.surveys.features.survey.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.surveys.R
import co.nimblehq.surveys.ui.theme.SurveysTheme

@Composable
fun SurveyPageErrorMessage(
    modifier: Modifier = Modifier,
    message: String,
    onRetryClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(message, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            onClick = onRetryClick,
            shape = CircleShape,
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Preview
@Composable
fun SurveyPageErrorMessagePreview() {
    SurveysTheme {
        SurveyPageErrorMessage(
            modifier = Modifier,
            message = "Error",
            onRetryClick = {},
        )
    }
}
