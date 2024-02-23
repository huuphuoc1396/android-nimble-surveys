package co.nimblehq.surveys.features.survey.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.nimblehq.surveys.ui.theme.SurveysTheme

@Composable
fun SurveyDetailScreen(
    id: String,
    navController: NavHostController = rememberNavController(),
) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "Survey Detail",
            style = MaterialTheme.typography.h3
        )
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "ID: $id",
            style = MaterialTheme.typography.body1,
        )
    }
}

@Preview
@Composable
fun SurveyDetailScreenPreview() {
    SurveysTheme {
        SurveyDetailScreen(id = "1")
    }
}
