package com.byteteam.bluesense.core.presentation.views.store.main.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R

@Composable
fun WaterFilterProductItem(){
    Card(
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(0.1f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .width(150.dp)
    ) {
        Column(Modifier.padding(bottom = 7.dp)) {
            Image(
                painter = painterResource(id = R.drawable.water_filter_dummy_1),
                contentDescription = stringResource(
                    R.string.water_filter_product
                ),

                contentScale = ContentScale.Crop,
                modifier = Modifier.height(96.dp)
            )
            Text(
                text = "Filter Air",
                modifier = Modifier.padding(
                    top = 8.dp,
                    start = 12.dp,
                    end = 12.dp
                )
            )
            Text(
                text = "Rp50.000",
                modifier = Modifier.padding(
                    top = 4.dp,
                    start = 12.dp,
                    end = 12.dp
                ),
                fontWeight = FontWeight.Bold
            )
            StarRating(rating = 3.9)
        }
    }
}