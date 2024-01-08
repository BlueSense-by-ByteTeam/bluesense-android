package com.byteteam.bluesense.core.presentation.views.store.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.views.store.detail.widgets.AffiliateShopTemplate
import com.byteteam.bluesense.core.presentation.views.store.detail.widgets.DescProductTemplate
import com.byteteam.bluesense.core.presentation.views.store.detail.widgets.NamePriceRatingTemplate
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.StarRating
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProductScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.bluesense)) })
        }
    ) { padding ->
        Column(
            modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.dummy_device_product),
                contentDescription = stringResource(
                    id = R.string.device_image
                ),
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .fillMaxWidth()
                    .height(268.dp),
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            )
            NamePriceRatingTemplate(
                name = stringResource(id = R.string.water_clean_detection),
                price = "Rp50.000",
                rating = 4.5
            )
            DescProductTemplate(desc = stringResource(R.string.dummy_device_desc))
            AffiliateShopTemplate(shopeeLink = "", tokopediaLink = "")
        }
    }
}


@Preview
@Composable
private fun PreviewDetailProductScreen() {
    BlueSenseTheme {
        Surface {
            DetailProductScreen()
        }
    }
}