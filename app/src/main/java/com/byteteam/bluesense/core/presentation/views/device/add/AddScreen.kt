package com.byteteam.bluesense.core.presentation.views.device.add

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.presentation.views.device.add.widgets.AddDeviceAlertContent
import com.byteteam.bluesense.core.presentation.widgets.BottomDialog
import com.byteteam.bluesense.core.presentation.widgets.ErrorAlertContent
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun AddScreen(
    startScanDevice: () -> Unit = {},
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    var openDialog by remember { mutableStateOf(false) }

    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (openDialog) {
            BottomDialog(onDismissRequest = {
                openDialog = false
            }) {
                AddDeviceAlertContent(onScan = {
                    openDialog = false
                    startScanDevice()
               }, onManual = {
                    openDialog = false
                    navHostController.navigate(Screens.AddDeviceForm.createRoute("no_data")) {
                        popUpTo(Screens.AddDevice.route) {
                            inclusive = true
                        }
                    }
                })
            }
        }

        Image(
            painter = painterResource(id = R.drawable.box_device),
            contentDescription = stringResource(
                id = R.string.device_image
            )
        )
        Text(
            text = stringResource(R.string.detect_water_quality), textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            stringResource(R.string.add_device_desc_2),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(
            onClick = { openDialog = true  },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = stringResource(id = R.string.add_device))
        }
        OutlinedButton(
            onClick = {
                navHostController.navigate(Screens.Store.route) {
                    popUpTo(Screens.AddDevice.route) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = stringResource(R.string.beli_sekarang))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            AddScreen()
        }
    }
}