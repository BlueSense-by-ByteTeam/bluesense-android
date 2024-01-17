package com.byteteam.bluesense.core.presentation.views.store.water_supplier

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.domain.model.WaterSupplierEntity


@Composable
fun SupplierItem(waterSupplierEntity: WaterSupplierEntity, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    fun openWhatsapp() {
        val url = "https://api.whatsapp.com/send?phone=${waterSupplierEntity.phone}"
        val i = Intent(Intent.ACTION_VIEW)
        i.setData(Uri.parse(url))
        startActivity(context, i, null)
    }

    fun openInstagram() {
        val url = "http://instagram.com/_u/${waterSupplierEntity.instagramUrl}"
        val i = Intent(Intent.ACTION_VIEW)
        i.setData(Uri.parse(url))
        startActivity(context, i, null)
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = modifier
            .height(136.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .width(152.dp)
                .height(136.dp)
        ) {
            AsyncImage(
                model = waterSupplierEntity.imageUrl,
                contentDescription = stringResource(
                    id = R.string.water_supplier_image
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
                    .fillMaxSize()
            )
        }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text(text = waterSupplierEntity.name, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(
                text = waterSupplierEntity.category,
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 4.dp
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
//            Text(
//                text = "Jl. Soekarno Hatta No 12",
//                modifier = Modifier.padding(
//                    top = 4.dp,
//                    bottom = 12.dp
//                ),
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
//            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.whatsapp_ic),
                    contentDescription = stringResource(R.string.whatsapp_icon),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { openWhatsapp() }
                )
                Image(
                    painter = painterResource(id = R.drawable.instagram_ic),
                    contentDescription = stringResource(R.string.instagram_icon),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { openInstagram() }
                )
            }
        }
    }
}
