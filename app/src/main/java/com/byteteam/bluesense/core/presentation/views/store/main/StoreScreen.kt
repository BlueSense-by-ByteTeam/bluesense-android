package com.byteteam.bluesense.core.presentation.views.store.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.BannerFilterDevice
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.WaterFilterProductTemplate
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.WaterSupplierTemplate
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun StoreScreen(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())) {
            BannerFilterDevice(modifier = Modifier.padding(horizontal = 24.dp))
            WaterFilterProductTemplate(
                navigateDetailProduct = {
                    navHostController.navigate(Screens.FilterRecommendation.createRoute(it))
                },
                navigateWaterFilterRecommendations = {
                    navHostController.navigate(Screens.FilterRecommendation.route)
                }
            )
            WaterSupplierTemplate(
                navigateWaterSupplierRecommendations = {
                    navHostController.navigate(Screens.WaterSupplierRecommendation.route)
                }
            )
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