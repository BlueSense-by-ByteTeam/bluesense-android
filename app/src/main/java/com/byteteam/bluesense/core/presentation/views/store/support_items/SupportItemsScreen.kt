package com.byteteam.bluesense.core.presentation.views.store.support_items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.WaterFilterEntity
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.presentation.views.store.support_items.widgets.WaterFilterItem
import com.byteteam.bluesense.core.presentation.widgets.BottomDialog
import com.byteteam.bluesense.core.presentation.widgets.BuyProductAlertContent
import com.byteteam.bluesense.core.presentation.widgets.ErrorHandler
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SupportItemsScreen(
    waterFiltersState: StateFlow<Resource<List<WaterFilterEntity>>>,
    getWaterFilters: () -> Unit,
) {
    var fetchingWaterFilter by remember { mutableStateOf(false) }
    var selectedProduct: WaterFilterEntity? by remember { mutableStateOf(null) }

    fun fetchOnceWaterFilter() {
        if (fetchingWaterFilter) {
            fetchingWaterFilter = false
            getWaterFilters()
        }
    }
    Column {
        selectedProduct?.let {
            BottomDialog(onDismissRequest = {
                selectedProduct = null
            }) {
                BuyProductAlertContent(waterFilterEntity = it)
            }
        }
        waterFiltersState.collectAsState().value.let {
            when (it) {
                is Resource.Success -> {
                    fetchingWaterFilter = false
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                    ) {
                        if (it.data != null && it.data.isEmpty()) {
                            item {
                                Text(text = "Belum ada data filter air")
                            }
                        }
                        items(it.data ?: listOf()) { item ->
                            WaterFilterItem(
                                waterFilterEntity = item,
                                onTap = {
                                    selectedProduct = item
                                }
                            )
                        }
                    }
                }

                is Resource.Loading -> {
                    fetchingWaterFilter = true
                    fetchOnceWaterFilter()
                    Row {
                        Text(text = "Memuat data filter air...")
                        CircularProgressIndicator()
                    }
                }

                is Resource.Error -> {
                    fetchingWaterFilter = false
                    ErrorHandler(
                        onPressRetry = { fetchOnceWaterFilter() },
                        errorText = it.message ?: "Error mengambil data filter air"
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun PreviewSupportItemScreen() {
    BlueSenseTheme {
        Surface {
//            SupportItemsScreen()
        }
    }
}