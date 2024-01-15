package com.byteteam.bluesense.core.presentation.views.store.water_supplier

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.WaterSupplierEntity
import com.byteteam.bluesense.core.presentation.widgets.ErrorHandler
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun WaterSupplierScreen(
    waterSuppliersState: StateFlow<Resource<List<WaterSupplierEntity>>>,
    getWaterSupplier: () -> Unit,
) {
    var fetchingWaterSupplier by remember { mutableStateOf(false) }

    fun fetchOnceWaterSupplier() {
        if (fetchingWaterSupplier) {
            fetchingWaterSupplier = false
            getWaterSupplier()
        }
    }
    waterSuppliersState.collectAsState().value.let {
        when (it) {
            is Resource.Success -> {
                fetchingWaterSupplier = false
                LazyColumn(
                    modifier = androidx.compose.ui.Modifier
                        .padding(horizontal = 24.dp)
                ) {
                    if(it.data != null  && it.data.isEmpty()){
                        item {
                        Text(text = "Belum ada data supplier air")
                        }
                    }
                    items(it.data ?: listOf()) { item ->
                        SupplierItem(item)
                    }
                }
            }

            is Resource.Loading -> {
                fetchingWaterSupplier = true
                fetchOnceWaterSupplier()
                Row {
                    Text(text = "Memuat data supplier air...")
                    CircularProgressIndicator()
                }
            }

            is Resource.Error -> {
                fetchingWaterSupplier = false
                ErrorHandler(
                    onPressRetry = { fetchOnceWaterSupplier() },
                    errorText = it.message ?: "Error mengambil data supplier air"
                )
            }
        }
    }

}

@Preview
@Composable
private fun WaterSupplierScreenPreview() {
    BlueSenseTheme {
        Surface {
//            WaterSupplierScreen()
        }
    }
}