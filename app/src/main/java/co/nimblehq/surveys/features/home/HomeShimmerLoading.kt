package co.nimblehq.surveys.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.surveys.features.components.shimmerBrush
import co.nimblehq.surveys.ui.theme.SurveysTheme

@Composable
fun HomeShimmerLoading(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .background(Color.Black)
                .statusBarsPadding()
                .fillMaxSize(),
    ) {
        Column(Modifier.padding(start = 20.dp, end = 20.dp, bottom = 28.dp)) {
            Spacer(Modifier.size(16.dp))
            HomeShimmerItem(Modifier.width(117.dp))
            Spacer(Modifier.size(14.dp))
            Row {
                HomeShimmerItem(Modifier.width(95.dp))
                Spacer(Modifier.weight(1f))
                AvatarShimmerItem(Modifier)
            }
            Spacer(Modifier.weight(1f))
            HomeShimmerItem(Modifier.width(37.dp))
            Spacer(Modifier.size(16.dp))
            HomeShimmerItem(Modifier.width(253.dp))
            Spacer(Modifier.size(8.dp))
            HomeShimmerItem(Modifier.width(120.dp))
            Spacer(Modifier.size(16.dp))
            HomeShimmerItem(Modifier.width(318.dp))
            Spacer(Modifier.size(8.dp))
            HomeShimmerItem(Modifier.width(208.dp))
        }
    }
}

@Composable
fun HomeShimmerItem(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .background(
                    brush = shimmerBrush(),
                    shape = RoundedCornerShape(18.dp),
                )
                .height(18.dp),
    )
}

@Composable
fun AvatarShimmerItem(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .size(36.dp)
                .background(
                    brush = shimmerBrush(),
                    shape = RoundedCornerShape(18.dp),
                ),
    )
}

@Preview
@Composable
fun HomeShimmerLoadingPreview() {
    SurveysTheme {
        HomeShimmerLoading()
    }
}
