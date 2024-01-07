package com.byteteam.bluesense.core.presentation.views.home.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R

@Composable
fun WaterFilterBanner(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFE4F1FF))
            .fillMaxWidth()
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.water_filter_w_background),
                contentDescription = stringResource(
                    R.string.filter_product
                ),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                modifier = Modifier.width(134.dp).height(112.dp).scale(1.8f).offset(x=-(8.dp))
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 20.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = "BLUEWATER", style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Filter Air Siap Minum",
                    modifier = Modifier
                        .padding(
                            top
                            = 12.dp
                        )
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(4.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "#ForYourHealthy",
                    modifier = Modifier.padding(
                        top
                        = 8.dp
                    )
                )

            }
        }
    }
}