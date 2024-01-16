package com.byteteam.bluesense.core.presentation.views.store.support_items.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.StarRating

@Composable
fun WaterFilterItem(
    waterFilterEntity: WaterFilterEntity,
    onTap: () -> Unit,
    modifier: Modifier = Modifier){
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = modifier
        .padding(bottom = 20.dp)
        .height(132.dp)) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .width(152.dp)
                .height(119.dp)
        ) {
            AsyncImage(
                model = waterFilterEntity.imageUrl,
                contentDescription = stringResource(
                    id = R.string.water_filter_product
                ),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {

            Text(text = stringResource(R.string.water_filter))
            Text(
                text = "Rp${waterFilterEntity.price.formatPrice()}",
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 4.dp
                ),
                fontWeight = FontWeight.Bold
            )
            StarRating(rating = waterFilterEntity.rating)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.see_detail),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onTap()
                }
            )
        }
    }
}