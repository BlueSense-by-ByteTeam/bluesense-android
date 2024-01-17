package com.byteteam.bluesense.core.presentation.views.home.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.model.DeviceLatestInfoEntity
import com.byteteam.bluesense.ui.theme.Green
import com.byteteam.bluesense.ui.theme.Yellow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DeviceInfoCard(
    statusDevice: StateFlow<Boolean>,
    onTapAddDevice: () -> Unit,
    onTapDetailDevice: (String) -> Unit,
    deviceEntity: DeviceEntity?,
    deviceData: StateFlow<Resource<DeviceLatestInfoEntity?>>,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = false,
    waterQualityRealtime: StateFlow<String?>,
    waterStatusRealtime: StateFlow<String?>,
) {
    var isBad by remember { mutableStateOf(false) }
    var isDataNull by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        Box(
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
                .background(
                    if (isDarkTheme) Color(0xFF3d3d3d) else Color(
                        0xFFF2F2F2
                    )
                )
                .padding(start = 20.dp, end = 20.dp, top = 24.dp)
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .width(166.dp),
                //                    contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.box_device),
                contentDescription = stringResource(
                    R.string.device_image
                )
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.device),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (deviceEntity == null) stringResource(R.string.no_device_added) else {
                            if (statusDevice.collectAsState().value) "Terhubung" else "Tidak Terhubung"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        color = if (deviceEntity == null || !statusDevice.collectAsState().value) Color.Red else Green
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (deviceEntity == null) MaterialTheme.colorScheme.primary else Color.LightGray)
                        .size(36.dp)
                        .clickable {
                            if (deviceEntity == null) onTapAddDevice()
                        }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.Center),
                        contentDescription = stringResource(R.string.add_icon)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .background(
                    if (deviceEntity != null) {
                        if (isBad) Yellow else MaterialTheme.colorScheme.primary
                    } else Color(
                        0xFFF2F2F2
                    )
                )
                .fillMaxWidth()
                .clickable { if (deviceEntity != null) onTapDetailDevice(deviceEntity.userDeviceId) }
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            deviceData.collectAsState().value.let {
                when (it) {
                    is Resource.Loading -> CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(16.dp)
                    )

                    is Resource.Error -> Text(
                        text = it.message ?: "Error",
                        color = if (isBad) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold
                    )

                    is Resource.Success -> {
                        isBad = when {
                            waterStatusRealtime.collectAsState().value != null -> waterStatusRealtime.collectAsState().value == "buruk"
                            else -> it.data?.quality == "buruk"
                        }

                        isDataNull = (it.data?.id == null)

                        Text(
                            text = when {
                                statusDevice.collectAsState().value && it.data?.quality == "buruk" -> "Air buruk!"
                                statusDevice.collectAsState().value && it.data?.quality == "baik" -> "Air aman"
                                else -> "-"
                            },
                            color =
                            if (isBad) Color.Black else {
                                if (it.data?.quality != null) MaterialTheme.colorScheme.onPrimary else Color.Black
                            },
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Text(
                text = "Detail",
                color = if (isBad) MaterialTheme.colorScheme.primary else {
                    if (isDataNull) {
                        Color.Gray
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    }
                },
                fontWeight = FontWeight.SemiBold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 12.dp))
                    .background(
                        if (deviceEntity != null) MaterialTheme.colorScheme.primary else Color(
                            0xFFF2F2F2
                        )
                    )
                    .weight(1f)
                    .height(96.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column {
                    Text(
                        text = "Kualitas Air",
                        color = if (isDataNull) Color.Black else MaterialTheme.colorScheme.onPrimary
                    )

                    deviceData.collectAsState().value.let {
                        when (it) {
                            is Resource.Loading -> CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(16.dp)
                            )

                            is Resource.Error -> Text(
                                text = it.message ?: "Error",
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            is Resource.Success -> Text(
                                text =
                                when {
                                    !statusDevice.collectAsState().value -> "-"
                                    statusDevice.collectAsState().value && waterStatusRealtime.collectAsState().value != null -> waterStatusRealtime.collectAsState().value!!.capitalize()
                                    statusDevice.collectAsState().value && it.data?.quality != null -> it.data?.quality!!.capitalize()
                                    else -> "-"
                                },
                                color =
                                if (it.data?.quality != null) MaterialTheme.colorScheme.onPrimary else Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                }
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomEnd = 12.dp))
                    .background(
                        if (deviceEntity != null) MaterialTheme.colorScheme.primary else Color(
                            0xFFF2F2F2
                        )
                    )
                    .weight(1f)
                    .height(96.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column {
                    Text(
                        text = "Status Air",
                        color = if (isDataNull) Color.Black else MaterialTheme.colorScheme.onPrimary
                    )
                    deviceData.collectAsState().value.let {
                        when (it) {
                            is Resource.Loading -> CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(16.dp)
                            )

                            is Resource.Error -> Text(
                                text = it.message ?: "Error",
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            is Resource.Success -> Text(
                                text =
                                when {
                                    statusDevice.collectAsState().value && waterStatusRealtime.collectAsState().value == "baik" || it.data?.status == "baik" -> "Dapat diminum"
                                    statusDevice.collectAsState().value && waterStatusRealtime.collectAsState().value == "buruk" || it.data?.status == "buruk" -> "Tidak Dapat diminum"
                                    else -> "-"
                                },
                                color = if (it.data?.quality != null) MaterialTheme.colorScheme.onPrimary else Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}