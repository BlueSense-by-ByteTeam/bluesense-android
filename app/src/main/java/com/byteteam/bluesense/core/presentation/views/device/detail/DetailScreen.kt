package com.byteteam.bluesense.core.presentation.views.device.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.views.device.detail.widgets.BannerWaterStatus
import com.byteteam.bluesense.core.presentation.views.device.detail.widgets.CardStatusTemplate
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import com.byteteam.bluesense.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back_icon)
                        )
                    }
                },
                title = { Text(text = "Detail") },
                actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = stringResource(
                            R.string.delete_icon
                        )
                    )
                }
            })
        }
    ) {
        Column(
            modifier
                .padding(it)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                "Penampungan Air",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Row(modifier = Modifier.padding(bottom = 20.dp)) {
                Text("Status Alat :")
                Text(" Terhubung", color = Color.Green)
            }
            BannerWaterStatus(modifier = Modifier.padding(bottom = 36.dp))
            Image(
                painter = painterResource(id = R.drawable.dummy_device_product),
                contentDescription = stringResource(
                    id = R.string.device_image
                ),
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(312.dp)
                    .height(268.dp)
            )
            CardStatusTemplate()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            DetailScreen()
        }
    }
}