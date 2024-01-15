package com.byteteam.bluesense.core.presentation.views.store.main.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import coil.compose.AsyncImage
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.domain.model.WaterFilterEntity
import com.byteteam.bluesense.core.presentation.helper.formatPrice

@Composable
fun WaterFilterProductItem(
    waterFilterEntity: WaterFilterEntity,
    onTap: (String) -> Unit
) {
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
            .clickable { onTap(waterFilterEntity.id) }
    ) {
        Column(Modifier.padding(bottom = 7.dp)) {
            AsyncImage(
                model = waterFilterEntity.imageUrl,
                contentDescription = stringResource(
                    R.string.water_filter
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(96.dp)
            )
            Text(
                text = waterFilterEntity.name,
                modifier = Modifier.padding(
                    top = 8.dp,
                    start = 12.dp,
                    end = 12.dp
                )
            )
            Text(
                text =  "Rp${waterFilterEntity.price.formatPrice()}",
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 4.dp,
                    start = 12.dp,
                    end = 12.dp
                ),
                fontWeight = FontWeight.Bold
            )
            StarRating(modifier= Modifier.padding(
                start = 12.dp,
                end = 12.dp
            ), rating = waterFilterEntity.rating)
        }
    }
}