package com.byteteam.bluesense.core.presentation.views.store.water_supplier

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.domain.model.WaterSupplierEntity

@Composable
fun SupplierItem(waterSupplierEntity: WaterSupplierEntity, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = modifier
            .height(136.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .width(152.dp)
                .height(136.dp)
        ) {
            AsyncImage(
                model = waterSupplierEntity.imageUrl,
                contentDescription = stringResource(
                    id = R.string.water_supplier_image
                ),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text(text = waterSupplierEntity.name)
            Text(
                text = waterSupplierEntity.category,
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 4.dp
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
            Text(
                text = "Jl. Soekarno Hatta No 12",
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 12.dp
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.whatsapp_ic),
                        contentDescription = stringResource(R.string.whatsapp_icon),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(32.dp).clickable {  }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.instagram_ic),
                        contentDescription = stringResource(R.string.instagram_icon),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(32.dp).clickable {  }
                    )
            }
        }
    }
}
