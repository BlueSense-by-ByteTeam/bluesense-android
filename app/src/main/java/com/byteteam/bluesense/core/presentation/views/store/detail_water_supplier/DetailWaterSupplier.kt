
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.data.source.remote.response.water_supplier_detail.WaterSupplierDetailResponse
import com.byteteam.bluesense.core.domain.model.LogEntity
import com.byteteam.bluesense.core.domain.model.WaterFilterEntity
import com.byteteam.bluesense.core.presentation.tokens.SortData
import com.byteteam.bluesense.core.presentation.views.store.detail.widgets.DescProductTemplate
import com.byteteam.bluesense.core.presentation.views.store.detail_water_supplier.widgets.ContactTemplate
import com.byteteam.bluesense.core.presentation.views.store.detail_water_supplier.widgets.NameTemplate
import com.byteteam.bluesense.core.presentation.widgets.ErrorHandler
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DetailWaterSupplier(
    getWaterSupplierDetail: () -> Unit,
    waterSupplierState: StateFlow<Resource<WaterSupplierDetailResponse>>,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        getWaterSupplierDetail()
    }
    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        waterSupplierState.collectAsState().value.let {
            when (it) {
                is Resource.Success -> {
                    AsyncImage(
                        model = it.data!!.data!!.imageUrl,
                        contentDescription = stringResource(
                            id = R.string.device_image
                        ),
                        modifier = Modifier
                            .padding(bottom = 40.dp)
                            .fillMaxWidth()
                            .height(268.dp),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center
                    )
                    NameTemplate(
                        name =  it.data!!.data!!.name ?: "",
                    )
                    SupplierWaterQualities(
                        list =  it.data!!.data!!.logs?.map { it!! }?.toList() ?: listOf(),
                    )
                    DescProductTemplate(desc = it.data!!.data!!.detailLocation ?: "")
                    ContactTemplate(phone = it.data!!.data!!.phone ?: "")
                }
                is Resource.Loading -> CircularProgressIndicator()
                is Resource.Error -> ErrorHandler(onPressRetry = {
                    getWaterSupplierDetail()
                }, errorText = it.message ?: "Error")
            }
        }

    }
}


fun getEntriesData(selectedData: SortData?, logs: List<LogEntity>?): List<ChartEntry> =
    when (selectedData) {
        SortData.PH -> logs?.mapIndexed { index, logEntity ->
            entryOf(index, logEntity.ph)
        }

        SortData.TDS -> logs?.mapIndexed { index, logEntity ->
            entryOf(index, logEntity.tds)
        }

        else -> logs?.mapIndexed { index, logEntity ->
            entryOf(index, logEntity.ph)
        }
    } ?: listOf()



@Preview
@Composable
private fun PreviewDetailProductScreen() {
    BlueSenseTheme {
        Surface {
//            DetailProductScreen()
        }
    }
}