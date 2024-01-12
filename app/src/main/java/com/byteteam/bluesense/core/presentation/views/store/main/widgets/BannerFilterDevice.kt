package com.byteteam.bluesense.core.presentation.views.store.main.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R

@Composable
fun BannerFilterDevice(modifier: Modifier = Modifier) {
    Row(
        modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF2F2F2))
            .padding(12.dp)
        , horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .width(125.dp)
                .height(125.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.box_device),
                contentDescription = stringResource(
                    id = R.string.device_image
                ),
                modifier = Modifier.align(Alignment.Center),
            )
        }
        Column {
            Text(
                text = stringResource(R.string.water_clean_detection),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.water_clean_detector_desc),
                style = MaterialTheme.typography.bodySmall
            )
            Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(8.dp)) {
                Text(text = stringResource(R.string.buy_now))
            }
        }
    }
}