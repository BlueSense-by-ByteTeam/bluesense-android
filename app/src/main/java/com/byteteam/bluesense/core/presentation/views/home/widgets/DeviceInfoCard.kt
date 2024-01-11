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
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.domain.model.DeviceEntity

@Composable
// TODO: change the device data type
fun DeviceInfoCard(
    onTapAddDevice: () -> Unit,
    onTapDetailDevice: (String) -> Unit,
    deviceData: DeviceEntity?,
    modifier: Modifier = Modifier
) {
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
                    .width(166.dp)
                    .offset(y = 68.dp),
                //                    contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.dummy_device_product),
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
                        text = stringResource(R.string.no_device_added),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Red
                    )
                }
                Box(
                    modifier = Modifier
                        .background(if (deviceData == null) MaterialTheme.colorScheme.primary else Color.LightGray)
                        .clip(RoundedCornerShape(4.dp))
                        .size(24.dp)
                        .clickable {
                            if (deviceData == null) onTapAddDevice()
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
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .clickable { if(deviceData != null) onTapDetailDevice(deviceData.id) }
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "-",
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Detail",
                color = MaterialTheme.colorScheme.onPrimary,
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
                    .background(MaterialTheme.colorScheme.primary)
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Column {
                    Text(
                        text = "Kualitas Air",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "-",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomEnd = 12.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Column {
                    Text(
                        text = "Status Air",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "lorem",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}