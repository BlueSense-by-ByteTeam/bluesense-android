package com.byteteam.bluesense.core.presentation.views.store.water_supplier

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.views.store.support_items.widgets.WaterFilterItem
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterSupplierScreen() {
    LazyColumn(
        modifier = androidx.compose.ui.Modifier
            .padding(horizontal = 24.dp)
    ) {
        items(12) { item ->
            SupplierItem()
        }
    }
}

@Preview
@Composable
private fun WaterSupplierScreenPreview() {
    BlueSenseTheme {
        Surface {
            WaterSupplierScreen()
        }
    }
}