package co.nimblehq.surveys.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.nimblehq.surveys.R
import co.nimblehq.surveys.domain.extensions.defaultEmpty
import co.nimblehq.surveys.ui.theme.EerieBlack
import co.nimblehq.surveys.ui.theme.SurveysTheme

@Composable
fun HomeDrawerContent(
    modifier: Modifier = Modifier,
    name: String?,
    avatarUrl: String?,
    onLogoutClick: () -> Unit,
) {
    Box(
        modifier
            .width(240.dp)
            .fillMaxHeight()
            .background(EerieBlack)
    ) {
        Column(
            Modifier
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.size(36.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = name.defaultEmpty(),
                    style = MaterialTheme.typography.h4
                )
                Avatar(modifier = Modifier.padding(top = 4.dp), avatarUrl = avatarUrl)
            }
            Divider(
                modifier = Modifier.padding(vertical = 20.dp),
                color = Color.White.copy(alpha = 0.2f)
            )

            TextButton(onClick = onLogoutClick, contentPadding = PaddingValues(0.dp)) {
                Text(
                    text = stringResource(R.string.logout),
                    style = MaterialTheme.typography.body1.copy(
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 20.sp,
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeDrawerContentPreview() {
    SurveysTheme {
        HomeDrawerContent(
            name = null,
            avatarUrl = null,
            onLogoutClick = {},
        )
    }
}