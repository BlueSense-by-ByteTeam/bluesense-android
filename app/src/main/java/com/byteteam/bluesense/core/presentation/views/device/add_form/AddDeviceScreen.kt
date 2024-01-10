package com.byteteam.bluesense.core.presentation.views.device.add_form

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_4
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.domain.model.CityEntity
import com.byteteam.bluesense.core.domain.model.DistrictEntity
import com.byteteam.bluesense.core.domain.model.ProvinceEntity
import com.byteteam.bluesense.core.presentation.widgets.DropDownInput
import com.byteteam.bluesense.core.presentation.widgets.InputField
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun AddDeviceFormScreen(
    provinces: List<ProvinceEntity>,
    cities: List<CityEntity>,
    districs: List<DistrictEntity>,
    getCitiesItem: (provinceId: Int) -> Unit,
    getDistrictItem: (provinceId: Int, cityId: Int) -> Unit
){
    var provinceId: Int? by remember { mutableStateOf(null)  }
    var cityId: Int? by remember { mutableStateOf(null)  }
    var districtId: Int? by remember { mutableStateOf(null)  }

    LaunchedEffect(provinceId){
        if(provinceId != null) getCitiesItem(provinceId!!)
    }

    LaunchedEffect(cityId){
        if(cityId != null) getDistrictItem(provinceId!!, cityId!!)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .fillMaxHeight()
            .padding(horizontal = 20.dp)) {
        Text(text = stringResource(R.string.add_device_desc), modifier = Modifier.padding(bottom=20.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(bottom = 24.dp)) {
            InputField(label = stringResource(R.string.device_name), modifier = Modifier.fillMaxWidth())
            InputField(label = stringResource(R.string.id_device), modifier = Modifier.fillMaxWidth())
            DropDownInput(label = stringResource(R.string.province), options = provinces.map { Pair(it.text, it.id.toIntOrNull() ?: 0) }, callbakOnSelect = {id -> provinceId = id },  modifier = Modifier.fillMaxWidth())
            DropDownInput(label = stringResource(R.string.city), options = cities.map { Pair(it.text, it.id.toIntOrNull() ?: 0) }, callbakOnSelect = {id -> cityId = id },  modifier = Modifier.fillMaxWidth())
            DropDownInput(label =  stringResource(R.string.district), options = districs.map { Pair(it.text, it.id.toIntOrNull() ?: 0) }, callbakOnSelect = { id -> districtId = id },  modifier = Modifier.fillMaxWidth())
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
//            AddDeviceFormScreen()
        }
    }
}