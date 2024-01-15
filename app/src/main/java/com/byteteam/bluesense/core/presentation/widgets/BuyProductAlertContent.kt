package com.byteteam.bluesense.core.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.domain.model.WaterFilterEntity
import com.byteteam.bluesense.core.presentation.views.store.detail.widgets.AffiliateShopTemplate

@Composable
fun BuyProductAlertContent(
    waterFilterEntity: WaterFilterEntity,
){
    Column(modifier = Modifier
        .height(412.dp)
        .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 48.dp)
        , horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.ecommerce_checkout_laptop_amico_1), contentDescription = null, modifier = Modifier.weight(1f))
        Text(text = "Pilih e-commerce", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom=12.dp))
        Text(text = "Pilih e-commerce kepercayaanmu", modifier = Modifier.padding(bottom=20.dp))
        AffiliateShopTemplate(tokopediaLink = waterFilterEntity.tokopediaUrl, shopeeLink = waterFilterEntity.shoppeUrl)
    }
}