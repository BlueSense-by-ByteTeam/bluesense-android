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
import com.byteteam.bluesense.core.data.event.SingleEvent
import com.byteteam.bluesense.core.domain.model.CityEntity
import com.byteteam.bluesense.core.domain.model.DistrictEntity
import com.byteteam.bluesense.core.domain.model.InputData
import com.byteteam.bluesense.core.domain.model.ProvinceEntity
import com.byteteam.bluesense.core.presentation.widgets.DropDownInput
import com.byteteam.bluesense.core.presentation.widgets.InputField
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun AddDeviceFormScreen(
    addDeviceScreenData: AddDeviceScreenData,
    idDeviceFromUrl: String?,
    provinces: List<ProvinceEntity>,
    cities: List<CityEntity>,
    districs: List<DistrictEntity>,
    getCitiesItem: (provinceId: Int) -> Unit,
    getDistrictItem: (provinceId: Int, cityId: Int) -> Unit
) {
    var provinceId: Int? by remember { mutableStateOf(null) }
    var cityId: Int? by remember { mutableStateOf(null) }
    var districtId: Int? by remember { mutableStateOf(null) }

    LaunchedEffect(provinceId) {
        if (provinceId != null) getCitiesItem(provinceId!!)
    }

    LaunchedEffect(cityId) {
        if (cityId != null) getDistrictItem(provinceId!!, cityId!!)
    }

    LaunchedEffect(Unit){
        if(idDeviceFromUrl != "no_data") addDeviceScreenData.updateId(idDeviceFromUrl!!)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .fillMaxHeight()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.add_device_desc),
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            InputField(
                label = stringResource(R.string.device_name),
                value = addDeviceScreenData.name.data,
                onUpdate = addDeviceScreenData.updateName,
                modifier = Modifier.fillMaxWidth()
            )
            InputField(
                label = stringResource(R.string.id_device),
                value =  addDeviceScreenData.id.data,
                onUpdate = addDeviceScreenData.updateId,
                modifier = Modifier.fillMaxWidth()
            )
            DropDownInput(
                label = stringResource(R.string.province),
                options = provinces.map { Pair(it.text, it.id.toIntOrNull() ?: 0) },
                callbakOnSelect = { id, text ->
                    provinceId = id
                    addDeviceScreenData.updateProvince(text)
                },
                modifier = Modifier.fillMaxWidth()
            )
            DropDownInput(
                label = stringResource(R.string.city),
                options = cities.map { Pair(it.text, it.id.toIntOrNull() ?: 0) },
                callbakOnSelect = { id, text ->
                    cityId = id
                    addDeviceScreenData.updateCity(text)
                },
                modifier = Modifier.fillMaxWidth()
            )
            DropDownInput(
                label = stringResource(R.string.district),
                options = districs.map { Pair(it.text, it.id.toIntOrNull() ?: 0) },
                callbakOnSelect = { id, text ->
                    districtId = id
                    addDeviceScreenData.updateDistrict(text)
                },
                modifier = Modifier.fillMaxWidth()
            )
            InputField(
                label = stringResource(R.string.address), value = addDeviceScreenData.address.data,
                onUpdate = addDeviceScreenData.updateAddress,
                modifier = Modifier.fillMaxWidth()
            )
            InputField(
                label = stringResource(R.string.water_source),
                value = addDeviceScreenData.waterSource.data,
                onUpdate = addDeviceScreenData.updateWaterSource,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(
            enabled = addDeviceScreenData.buttonEnabled,
            onClick = addDeviceScreenData.postDevice,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = stringResource(R.string.add_device))
        }
    }
}

data class AddDeviceScreenData(
    val name: InputData,
    val id: InputData,
    val province: InputData,
    val city: InputData,
    val district: InputData,
    val address: InputData,
    val waterSource: InputData,
    val buttonEnabled: Boolean,
    val updateId: (String) -> Unit,
    val updateName: (String) -> Unit,
    val updateProvince: (String) -> Unit,
    val updateCity: (String) -> Unit,
    val updateDistrict: (String) -> Unit,
    val updateAddress: (String) -> Unit,
    val updateWaterSource: (String) -> Unit,
    val eventMessage: Flow<SingleEvent>,
    val postDevice: () -> Unit,
)


@Preview(showBackground = true, device = PIXEL_4)
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
//            AddDeviceFormScreen()
        }
    }
}