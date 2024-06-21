package com.byteteam.bluesense.core.presentation.views.store.detail_water_supplier.widgets

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.byteteam.bluesense.R

@Composable
fun ContactTemplate(
    phone: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    fun openWhatsapp() {
        val url = "https://api.whatsapp.com/send?phone=${phone}"
        val i = Intent(Intent.ACTION_VIEW)
        i.setData(Uri.parse(url))
        ContextCompat.startActivity(context, i, null)
    }
    val primaryColor = MaterialTheme.colorScheme.primary
    val border = BorderStroke(width = 1.dp, color = primaryColor)
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {

        OutlinedButton(
            shape = RoundedCornerShape(12.dp),
            border = border,
            modifier = Modifier.fillMaxWidth(),
            onClick = { openWhatsapp() }) {
            Image(
                painter = painterResource(id = R.drawable.whatsapp_ic),
                contentDescription = stringResource(
                    R.string.tokopedia_logo
                ),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Whatsapp", color = primaryColor)
        }
    }
}