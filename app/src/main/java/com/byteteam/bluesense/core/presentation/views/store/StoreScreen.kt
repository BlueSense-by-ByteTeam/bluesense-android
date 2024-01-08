package com.byteteam.bluesense.core.presentation.views.store

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.views.store.widgets.BannerFilterDevice
import com.byteteam.bluesense.core.presentation.views.store.widgets.StarRating
import com.byteteam.bluesense.core.presentation.views.store.widgets.WaterFilterProductTemplate
import com.byteteam.bluesense.core.presentation.views.store.widgets.WaterSupplierTemplate
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