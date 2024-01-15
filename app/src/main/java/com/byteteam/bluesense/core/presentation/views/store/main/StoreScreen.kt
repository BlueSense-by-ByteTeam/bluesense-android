package com.byteteam.bluesense.core.presentation.views.store.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.WaterFilterEntity
import com.byteteam.bluesense.core.domain.model.WaterSupplierEntity
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.BannerFilterDevice
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.WaterFilterProductTemplate
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.WaterSupplierTemplate
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun StoreScreen(
    navHostController: NavHostController = rememberNavController(),
    waterSuppliersState: StateFlow<Resource<List<WaterSupplierEntity>>>,
    waterFiltersState: StateFlow<Resource<List<WaterFilterEntity>>>,
    getWaterSupplier: () -> Unit,
    getWaterFilter: () -> Unit,
    modifier: Modifier = Modifier
) {
    var fetchingWaterSupplier by remember { mutableStateOf(false) }
    var fetchingWaterFilter by remember { mutableStateOf(false) }
    fun fetchOnceWaterSupplier() {
        if (fetchingWaterSupplier) {
            fetchingWaterSupplier = false
            getWaterSupplier()
        }
    }

    fun fetchOnceWaterFilter() {
        if (fetchingWaterFilter) {
            fetchingWaterFilter = false
            getWaterFilter()
        }
    }
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
    ) {
        BannerFilterDevice(modifier = Modifier.padding(horizontal = 24.dp))
        waterFiltersState.collectAsState().value.let {
            when (it) {
                is Resource.Success -> {
                    fetchingWaterFilter = false
                    WaterFilterProductTemplate(
                        waterFilterEntities = it.data ?: listOf(),
                        navigateDetailProduct = {
                            navHostController.navigate(Screens.FilterRecommendation.createRoute(it))
                        },
                        navigateWaterFilterRecommendations = {
                            navHostController.navigate(Screens.FilterRecommendation.route)
                        }
                    )
                }
                is Resource.Loading -> {
                    fetchingWaterFilter = true
                    fetchOnceWaterFilter()
                    Row {
                        Text(text = "Memuat data supplier air...")
                        CircularProgressIndicator()
                    }
                }
                is Resource.Error -> {
                    fetchingWaterSupplier = false
                    Column {
                        Text(text = it.message ?: "Error mengambil data filter air")
                        Button(onClick = { fetchOnceWaterFilter() }) {
                            Text(text = "Muat ulang")
                        }
                    }
                }
            }
        }
        waterSuppliersState.collectAsState().value.let {
            when (it) {
                is Resource.Success -> {
                    fetchingWaterSupplier = false
                    WaterSupplierTemplate(
                        waterSupplierEntities = it.data ?: listOf(),
                        navigateWaterSupplierRecommendations = {
                            navHostController.navigate(Screens.WaterSupplierRecommendation.route)
                        }
                    )
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
                    Column {
                        Text(text = it.message ?: "Error mengambil data supplier air")
                        Button(onClick = { fetchOnceWaterSupplier() }) {
                            Text(text = "Muat ulang")
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
//    BlueSenseTheme {
//        Surface {
//            StoreScreen(
//                rememberNavController(),
//                MutableStateFlow(Resource.Success(listOf())),
//                MutableStateFlow(Resource.Success(listOf())),
//            )
//        }
//    }
}