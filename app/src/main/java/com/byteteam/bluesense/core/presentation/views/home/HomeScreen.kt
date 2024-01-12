package com.byteteam.bluesense.core.presentation.views.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.model.DeviceLatestInfoEntity
import com.byteteam.bluesense.core.presentation.views.home.widgets.HomeScreenContent
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    statusDevice: StateFlow<Boolean>,
    devices: StateFlow<Resource<List<DeviceEntity>>> = MutableStateFlow(Resource.Success(listOf())),
    detailDevice: StateFlow<Resource<DeviceLatestInfoEntity?>> = MutableStateFlow(Resource.Loading()),
    cbOnDeviceConnected: (DeviceEntity) -> Unit = {},
    getDevices: () -> Unit = {},
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val isRefreshing by remember {
        mutableStateOf(false)
    }
    val state = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        getDevices()
    })

    var dataEntity: DeviceEntity? by remember { mutableStateOf(null) }

    LaunchedEffect(dataEntity){
        if(dataEntity != null) cbOnDeviceConnected(dataEntity!!)
    }
//    var isInstantiate by remember{ mutableStateOf(false)  }

    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Box() {
            devices.collectAsState().value.let {
                when (it) {
                    is Resource.Loading -> {
                        getDevices()
                        CircularProgressIndicator()
                    }

                    is Resource.Error -> Text(text = "error x${it.message}" ?: "Error")
                    is Resource.Success -> {
                        if (it.data?.get(0) != null) dataEntity = it.data[0]
                        HomeScreenContent(
                            statusDevice = statusDevice,
                            deviceEntity = it.data?.get(0),
                            deviceInfo = detailDevice,
                            navHostController = navHostController,
                            modifier = modifier
//                            .fillMaxSize()
//                            .verticalScroll(rememberScrollState())
                                .pullRefresh(state),
                        )
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = isRefreshing, state = state,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }
    }

}

@Preview(device = Devices.PIXEL_4)
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            HomeScreen(MutableStateFlow(true))
        }
    }
}
