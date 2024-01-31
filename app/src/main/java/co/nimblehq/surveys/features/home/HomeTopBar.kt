package co.nimblehq.surveys.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.surveys.R
import co.nimblehq.surveys.domain.extensions.format
import co.nimblehq.surveys.ui.theme.SurveysTheme
import java.util.Date

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    onAccountClick: () -> Unit = {},
) {
    Column(
        modifier.padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Text(
            text = Date().format("EEEE, MMMM dd").uppercase(),
            style = MaterialTheme.typography.subtitle2,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Row(Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.today),
                style = MaterialTheme.typography.h5,
            )

            Avatar(
                avatarUrl = avatarUrl,
                onAccountClick = onAccountClick,
            )
        }
    }
}

@Preview
@Composable
fun HomeTopBarPreview() {
    SurveysTheme {
        HomeTopBar()
    }
}
