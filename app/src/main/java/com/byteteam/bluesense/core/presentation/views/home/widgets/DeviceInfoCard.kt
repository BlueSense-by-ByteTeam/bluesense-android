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
import androidx.compose.foundation.layout.offset
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
) {
    var isBad by remember { mutableStateOf(false)  }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        Box(
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
                .background(
                    if (isSystemInDarkTheme()) Color(0xFF3d3d3d) else Color(
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
                        .background(if (deviceEntity == null) MaterialTheme.colorScheme.primary else Color.LightGray)
                        .clip(RoundedCornerShape(4.dp))
                        .size(24.dp)
                        .clickable {
                            if (deviceEntity == null) onTapAddDevice()
                        }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = Color.White,
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
                    } else Color.LightGray
                )
                .fillMaxWidth()
                .clickable { if (deviceEntity != null) onTapDetailDevice(deviceEntity.id) }
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            deviceData.collectAsState().value.let {
                when (it) {
                    is Resource.Loading -> CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp, modifier = Modifier.size(16.dp))
                    is Resource.Error -> Text(
                        text = it.message ?: "-",
                        color = if(isBad) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold
                    )

                    is Resource.Success -> {
                        isBad = it.data?.quality == "buruk"
                        Text(
                            text = it.data?.quality ?: "belum ada data",
                            color = if(isBad) Color.Black else  MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Text(
                text = "Detail",
                color = if(isBad) MaterialTheme.colorScheme.primary else  MaterialTheme.colorScheme.onPrimary,
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
                    .background(if (deviceEntity != null) MaterialTheme.colorScheme.primary else Color.LightGray)
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Column {
                    Text(
                        text = "Kualitas Air",
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    deviceData.collectAsState().value.let {
                        when (it) {
                            is Resource.Loading -> CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp, modifier = Modifier.size(16.dp))
                            is Resource.Error -> Text(
                                text = it.message ?: "-",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            is Resource.Success -> Text(
                                text = it.data?.quality ?: "belum ada data",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                }
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomEnd = 12.dp))
                    .background(if (deviceEntity != null) MaterialTheme.colorScheme.primary else Color.LightGray)
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Column {
                    Text(
                        text = "Status Air",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    deviceData.collectAsState().value.let {
                        when (it) {
                            is Resource.Loading -> CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp, modifier = Modifier.size(16.dp))
                            is Resource.Error -> Text(
                                text = it.message ?: "-",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            is Resource.Success -> Text(
                                text = it.data?.status ?: "belum ada data",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}