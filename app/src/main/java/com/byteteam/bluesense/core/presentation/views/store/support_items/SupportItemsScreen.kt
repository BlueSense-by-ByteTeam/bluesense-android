package com.byteteam.bluesense.core.presentation.views.store.support_items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.StarRating
import com.byteteam.bluesense.core.presentation.views.store.support_items.widgets.WaterFilterItem
import com.byteteam.bluesense.core.presentation.views.store.water_supplier.SupplierItem
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun SupportItemsScreen(
    navHostController: NavHostController = rememberNavController()
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ) {
        items(12) { item ->
            WaterFilterItem(navigateDetailItem = {id ->
                navHostController.navigate(Screens.FilterRecommendation.createRoute(id))
            })
        }
    }
}

@Preview
@Composable
private fun PreviewSupportItemScreen() {
    BlueSenseTheme {
        Surface {
            SupportItemsScreen()
        }
    }
}