package com.byteteam.bluesense.core.presentation.views.store.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import kotlin.math.roundToInt

@Composable
fun StarRating(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Box {
        Row(modifier){
            for (index in 1..5) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "star rating background",
                    tint = Color(0xFFE7DAA9))
            }
        }
        Row(modifier) {
            for (index in 1..rating.roundToInt()) {
                Icon(
                    imageVector = if (index <= rating) {
                        Icons.Default.Star
                    } else {
                        if (isCurrentHalfStar(index, rating)) {
                            Icons.Default.StarHalf
                        } else {
                            Icons.Outlined.StarOutline
                        }
                    }, contentDescription = "star rating",
                    tint = Color(0xFFFFD233)
                )
            }
        }
    }
}

private fun isCurrentHalfStar(index: Int, rating: Double): Boolean{
    return (rating % 1) != 0.0 && rating.roundToInt() >= index
}
@Preview
@Composable
private fun PreviewStarRating() {
    BlueSenseTheme {
        Surface {
            StarRating(rating = 3.9)
        }
    }
}