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
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.BannerFilterDevice
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.WaterFilterProductTemplate
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.WaterSupplierTemplate
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
            WaterFilterProductTemplate()
            WaterSupplierTemplate()
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