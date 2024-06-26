package com.byteteam.bluesense.core.presentation.views.store.main.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.domain.model.WaterSupplierEntity
import com.byteteam.bluesense.core.helper.Screens

@Composable
fun WaterSupplierTemplate(
    waterSupplierEntities: List<WaterSupplierEntity>,
    navHostController: NavHostController,
    navigateWaterSupplierRecommendations: () -> Unit
) {
    Column {
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
                    modifier = Modifier.clickable { navigateWaterSupplierRecommendations() })
            }
            Text(
                text = stringResource(R.string.your_water_need_is_here),
            )
        }
        LazyRow(contentPadding = PaddingValues(start = 24.dp)) {
            if (waterSupplierEntities.isEmpty()) {
                item {
                    Text(text = "Belum ada data supplier air.")
                }
            }
            items(waterSupplierEntities) {
                SupplierItem(it, onTap = {
                    navHostController.navigate(Screens.WaterSupplierRecommendation.createRoute(it.id))
                })
            }
        }
    }
}