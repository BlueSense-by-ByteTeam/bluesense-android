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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.WaterFilterEntity
import com.byteteam.bluesense.core.presentation.helper.formatPrice
import com.byteteam.bluesense.core.presentation.views.store.detail.widgets.AffiliateShopTemplate
import com.byteteam.bluesense.core.presentation.views.store.detail.widgets.DescProductTemplate
import com.byteteam.bluesense.core.presentation.views.store.detail.widgets.NamePriceRatingTemplate
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.StarRating
import com.byteteam.bluesense.core.presentation.widgets.ErrorHandler
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import kotlinx.coroutines.flow.StateFlow
import org.xml.sax.ErrorHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProductScreen(
    getFeaturedWaterFilter: () -> Unit,
    waterFilterState: StateFlow<Resource<WaterFilterEntity>>,
    modifier: Modifier = Modifier
) {

    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        waterFilterState.collectAsState().value.let {
            when (it) {
                is Resource.Success -> {
                    AsyncImage(
                        model = it.data!!.imageUrl,
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
                        price = "Rp${650000L.formatPrice()}",
                        rating = it.data.rating
                    )
                    DescProductTemplate(desc = it.data.description)
                    AffiliateShopTemplate(shopeeLink = it.data.shoppeUrl, tokopediaLink = it.data.tokopediaUrl)
                }

                is Resource.Loading -> CircularProgressIndicator()
                is Resource.Error -> ErrorHandler(onPressRetry = {
                    getFeaturedWaterFilter()
                }, errorText = it.message ?: "Error")
            }
        }

    }
}


@Preview
@Composable
private fun PreviewDetailProductScreen() {
    BlueSenseTheme {
        Surface {
//            DetailProductScreen()
        }
    }
}