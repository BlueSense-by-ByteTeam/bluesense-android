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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.domain.model.WaterFilterEntity
import com.byteteam.bluesense.core.presentation.widgets.BottomDialog
import com.byteteam.bluesense.core.presentation.widgets.BuyProductAlertContent
import com.byteteam.bluesense.core.presentation.widgets.ErrorAlertContent

@Composable
fun WaterFilterProductTemplate(
    waterFilterEntities: List<WaterFilterEntity>,
    navigateDetailProduct: (String) -> Unit,
    navigateWaterFilterRecommendations:  () -> Unit
) {
    var selectedProduct: WaterFilterEntity? by remember { mutableStateOf(null) }
        Column(
            Modifier
                .padding(bottom = 20.dp, top = 24.dp)
        ) {
            selectedProduct?.let {
                BottomDialog(onDismissRequest = {
                    selectedProduct = null
                }) {
                    BuyProductAlertContent(waterFilterEntity = it)
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
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
                    modifier = Modifier.clickable { navigateWaterFilterRecommendations() })
            }
            Text(
                text = stringResource(R.string.use_this_to_cleanse_your_water),
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 20.dp)
            )
            LazyRow(contentPadding = PaddingValues(start = 24.dp)) {
                if (waterFilterEntities.isEmpty()) {
                    item {
                        Text(text = "Belum ada data filter air.")
                    }
                }
                items(waterFilterEntities) { item ->
                    WaterFilterProductItem(
                        waterFilterEntity = item,
                        onTap = {
                            selectedProduct = item
                        }
                    )
                }
            }
        }
}