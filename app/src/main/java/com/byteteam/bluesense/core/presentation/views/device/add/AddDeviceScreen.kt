package com.byteteam.bluesense.core.presentation.views.device.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_4
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.widgets.InputField
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun AddDeviceScreen(){
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
//            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .fillMaxHeight()
            .padding(horizontal = 20.dp)) {
        Text(text = stringResource(R.string.add_device_desc), modifier = Modifier.padding(bottom=20.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(bottom = 24.dp)) {
            InputField(label = stringResource(R.string.device_name), modifier = Modifier.fillMaxWidth())
            InputField(label = stringResource(R.string.id_device), modifier = Modifier.fillMaxWidth())
            InputField(label = stringResource(R.string.province), modifier = Modifier.fillMaxWidth())
            InputField(label = stringResource(R.string.city), modifier = Modifier.fillMaxWidth())
            InputField(label = stringResource(R.string.district), modifier = Modifier.fillMaxWidth())
            InputField(label = stringResource(R.string.address), modifier = Modifier.fillMaxWidth())
            InputField(label = stringResource(R.string.water_source), modifier = Modifier.fillMaxWidth())
        }
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
            Text(text = stringResource(R.string.add_device))
        }
    }
}

@Preview(showBackground = true, device = PIXEL_4)
@Composable
private fun Preview(){
    BlueSenseTheme {
        Surface {
            AddDeviceScreen()
        }
    }
}