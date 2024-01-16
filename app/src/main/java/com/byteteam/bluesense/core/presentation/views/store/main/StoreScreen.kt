package com.byteteam.bluesense.core.presentation.views.store.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.byteteam.bluesense.core.domain.model.WaterSupplierEntity
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.BannerFilterDevice
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.WaterFilterProductTemplate
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.WaterSupplierTemplate
import kotlinx.coroutines.flow.StateFlow

@Composable
fun StoreScreen(
    featuredWaterFilterState: StateFlow<Resource<WaterFilterEntity>>,
    waterSuppliersState: StateFlow<Resource<List<WaterSupplierEntity>>>,
    waterFiltersState: StateFlow<Resource<List<WaterFilterEntity>>>,
    getWaterSupplier: () -> Unit,
    getWaterFilter: () -> Unit,
    getFeaturedWaterFilter: () -> Unit,
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    var fetchingFeaturedWaterFilter by remember { mutableStateOf(false) }
    var fetchingWaterSupplier by remember { mutableStateOf(false) }
    var fetchingWaterFilter by remember { mutableStateOf(false) }
    fun fetchOnceFeaturedWaterFilter() {
        if (featuredWaterFilterState.value is Resource.Loading && !fetchingFeaturedWaterFilter) {
            fetchingFeaturedWaterFilter = true
            getFeaturedWaterFilter()
        }
    }

    fun fetchOnceWaterSupplier() {
        if (waterSuppliersState.value is Resource.Loading && !fetchingWaterSupplier) {
            fetchingWaterSupplier = true
            getWaterSupplier()
        }
    }

    fun fetchOnceWaterFilter() {
        if (waterFiltersState.value is Resource.Loading && !fetchingWaterFilter) {
            fetchingWaterFilter = true
            getWaterFilter()
        }
    }

    LaunchedEffect(Unit){
        fetchOnceFeaturedWaterFilter()
        fetchOnceWaterFilter()
        fetchOnceWaterSupplier()
    }

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
    ) {
        featuredWaterFilterState.collectAsState().value.let {
            when (it) {
                is Resource.Success -> {
                    fetchingFeaturedWaterFilter = false
                    if (it.data == null) {
                        Text(text = "Belum ada data filter air.")
                    } else {
                        BannerFilterDevice(
                            navigateDetail = { navHostController.navigate(Screens.DetailFilterDevice.createRoute(it.data.id))  },
                            waterFilterEntity = it.data,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                }
                is Resource.Loading -> {
                    Row(Modifier.height(240.dp).padding(horizontal = 24.dp)) {
                        Text(text = "Memuat data filter air...")
                        CircularProgressIndicator()
                    }
                }

                is Resource.Error -> {
                    fetchingFeaturedWaterFilter = false
                    Column(Modifier.height(240.dp).padding(horizontal = 24.dp)) {
                        Text(text = it.message ?: "Error mengambil data filter air")
                        Button(onClick = { fetchOnceWaterFilter() }) {
                            Text(text = "Muat ulang")
                        }
                    }
                }
            }
        }

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
                    Row(Modifier.height(240.dp).padding(horizontal = 24.dp)) {
                        Text(text = "Memuat data supplier air...")
                        CircularProgressIndicator()
                    }
                }

                is Resource.Error -> {
                    fetchingWaterFilter = false
                    Column(Modifier.height(240.dp).padding(horizontal = 24.dp)) {
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
                    fetchOnceWaterSupplier()
                    Row(Modifier.height(240.dp).padding(horizontal = 24.dp)) {
                        Text(text = "Memuat data supplier air...")
                        CircularProgressIndicator()
                    }
                }

                is Resource.Error -> {
                    fetchingWaterSupplier = false
                    Column(Modifier.height(240.dp).padding(horizontal = 24.dp)) {
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