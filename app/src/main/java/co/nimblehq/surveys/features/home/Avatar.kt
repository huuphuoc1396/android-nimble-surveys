package co.nimblehq.surveys.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.nimblehq.surveys.R
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    avatarUrl: String?,
    onAccountClick: () -> Unit = {},
) {
    Image(
        painter =
            avatarUrl?.let {
                rememberAsyncImagePainter(
                    model =
                        ImageRequest.Builder(LocalContext.current)
                            .data(avatarUrl)
                            .placeholder(R.drawable.ic_place_holder_account)
                            .build(),
                )
            } ?: painterResource(id = R.drawable.ic_place_holder_account),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier =
            modifier
                .size(36.dp)
                .clip(CircleShape)
                .clickable(onClick = onAccountClick),
    )
}
