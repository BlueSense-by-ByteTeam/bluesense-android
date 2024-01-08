package com.byteteam.bluesense.core.presentation.views.store

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.views.store.widgets.BannerFilterDevice
import com.byteteam.bluesense.core.presentation.views.store.widgets.StarRating
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.bluesense)) })
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())) {
            BannerFilterDevice(modifier = Modifier.padding(horizontal = 24.dp))
            Column {
                Column(
                    Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 20.dp, top = 24.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = stringResource(R.string.increate_water_quality),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = stringResource(R.string.see_all),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable { })
                    }
                    Text(
                        text = stringResource(R.string.use_this_to_cleanse_your_water),
                    )
                }
                LazyRow(contentPadding = PaddingValues(start = 24.dp)) {
                    items(12) {
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
                }
                Column(
                    Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 20.dp, top = 24.dp)
                ) {

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = stringResource(R.string.nearest_water_supplier),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = stringResource(R.string.see_all),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable { })
                    }
                    Text(
                        text = stringResource(R.string.your_water_need_is_here),
                    )
                }
                LazyRow(contentPadding = PaddingValues(start = 24.dp)) {
                    items(12) {
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
                                    painter = painterResource(id = R.drawable.water_supplier_dummy),
                                    contentDescription = stringResource(R.string.water_supplier_image),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.height(96.dp)
                                )
                                Text(
                                    text = "Tirta Jaya",
                                    modifier = Modifier.padding(
                                        top = 8.dp,
                                        start = 12.dp,
                                        end = 12.dp
                                    )
                                )
                                Text(
                                    text = "Water tank",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                    modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewStoreScreen() {
    BlueSenseTheme {
        Surface {
            StoreScreen()
        }
    }
}