package com.byteteam.bluesense.core.presentation.views.statistic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.LogEntity
import com.byteteam.bluesense.core.domain.model.LogHistoryEntity
import com.byteteam.bluesense.core.presentation.tokens.SortData
import com.byteteam.bluesense.core.presentation.tokens.SortDateLog
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.ChartTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.DescTextTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.LegendTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.OptionSortDateTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.OptionStatTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.StatsTextTemplate
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlinx.coroutines.flow.StateFlow
import java.text.DecimalFormat
import kotlin.random.Random

val df = DecimalFormat("#.##")

@Composable
fun StatisticScreen(
    getHistoryToday: () -> Unit,
    getHistoryWeek: () -> Unit,
    getHistoryMonth: () -> Unit,
    getHistoryYear: () -> Unit,
    historyState: StateFlow<Resource<LogHistoryEntity>>,
    modifier: Modifier = Modifier
) {
    var selectedDate: SortDateLog? by remember { mutableStateOf(SortDateLog.TODAY) }
    var selectedData: SortData? by remember { mutableStateOf(SortData.QUALITY) }
    val sortDates = mapOf(
        stringResource(R.string.day) to SortDateLog.TODAY,
        stringResource(R.string.week) to SortDateLog.WEEK,
        stringResource(R.string.month) to SortDateLog.MONTH,
        stringResource(R.string.year) to SortDateLog.YEAR,
    )
    val sortDatas = mapOf(
        stringResource(R.string.quality) to SortData.QUALITY,
        stringResource(R.string.status) to SortData.STATUS,
        stringResource(R.string.tds) to SortData.TDS,
        stringResource(R.string.ph) to SortData.PH,
    )

    var isFetchingData by remember { mutableStateOf(false) }
    fun fetchOnce() {
        if (isFetchingData) {
            isFetchingData = false
            getHistoryToday()
        }
    }
    LaunchedEffect(selectedDate) {
        when (selectedDate) {
            SortDateLog.TODAY -> getHistoryToday()
            SortDateLog.WEEK -> getHistoryWeek()
            SortDateLog.MONTH -> getHistoryMonth()
            SortDateLog.YEAR -> getHistoryYear()
            else -> getHistoryToday()
        }
    }


    Column(
        modifier
            .verticalScroll(rememberScrollState())
    ) {
        OptionSortDateTemplate(
            disabledClick = isFetchingData,
            sortDates = sortDates,
            selectedDate = selectedDate,
            onClick = { selectedDate = it })
        historyState.collectAsState().value.let {
            when (it) {
                is Resource.Loading -> {
                    isFetchingData = true
                    Box(
                        modifier = Modifier
                            .height(194.dp)
                            .fillMaxWidth()
                            .padding(24.dp)
                            .size(24.dp)
                    ) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }

                is Resource.Error -> {
                    isFetchingData = false
                    Column(
                        Modifier.height(194.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Gagal mengambil data riwayat log perangkat")
                        Button(onClick = { getHistoryToday() }) {
                            Text(text = "Muat ulang")
                        }
                    }
                }

                is Resource.Success -> {
                    isFetchingData = false
                    val entries = getEntriesData(selectedData, it?.data?.logs)
                    val stats = getMinMaxAverage(selectedData, it?.data)
                    ChartTemplate(chartEntryModelProducer = ChartEntryModelProducer(entries))
                    if (selectedData == SortData.STATUS || selectedData == SortData.QUALITY) LegendTemplate()
                    OptionStatTemplate(
                        disabledClick = isFetchingData,
                        sortDatas = sortDatas,
                        selectedData = selectedData,
                        onClick = { selectedData = it })
                    StatsTextTemplate(
                        min = stats.first,
                        max = stats.second,
                        average = stats.third,
                    )
                }
            }
        }

        when (selectedData) {
            SortData.STATUS -> DescTextTemplate(
                title = stringResource(R.string.water_status_title),
                desc = stringResource(
                    R.string.water_status_desc
                )
            )

            SortData.QUALITY -> DescTextTemplate(
                title = stringResource(R.string.water_quality_title),
                desc = stringResource(R.string.water_quality_desc)
            )

            SortData.TDS -> DescTextTemplate(
                title = stringResource(R.string.tds_title),
                desc = stringResource(R.string.tds_desc)
            )

            SortData.PH -> DescTextTemplate(
                title = stringResource(R.string.ph_title),
                desc = stringResource(R.string.ph_desc)
            )

            else -> null
        }
    }
}

fun getEntriesData(selectedData: SortData?, logs: List<LogEntity>?): List<FloatEntry> =
    when (selectedData) {
        SortData.PH -> logs?.mapIndexed { index, logEntity ->
            entryOf(index, logEntity.ph)
        }

        SortData.TDS -> logs?.mapIndexed { index, logEntity ->
            entryOf(index, logEntity.tds)
        }

        SortData.QUALITY -> logs?.mapIndexed { index, logEntity ->
            entryOf(index, if (logEntity.quality == 0) 1 else 2)
        }

        SortData.STATUS -> logs?.mapIndexed { index, logEntity ->
            entryOf(index, if (logEntity.status == 0) 1 else 2)
        }

        else -> logs?.mapIndexed { index, logEntity ->
            entryOf(index, logEntity.ph)
        }
    } ?: listOf()

fun getMinMaxAverage(
    selectedData: SortData?,
    history: LogHistoryEntity?
): Triple<String, String, String> =
    when (selectedData) {

        SortData.PH -> Triple(
            df.format(history?.minPh).toString(),
            df.format(history?.maxPh).toString(),
            df.format(history?.averagePh).toString()
        )

        SortData.TDS -> Triple(
            df.format(history?.minTds).toString(),
            df.format(history?.maxTds).toString(),
            df.format(history?.averageTds).toString()
        )

        SortData.QUALITY -> Triple(
            "-",
            "-",
            if (history?.averageQuality == "baik") "Dapat diminum" else "Tidak dapat diminum"
        )

        SortData.STATUS -> Triple("-", "-", history?.averageStatus.toString())

        else -> Triple("-", "-", history?.averagePh.toString())
    }


fun getRandomEntries() = List(4) { entryOf(it, Random.nextFloat() * 16f) }

@Preview
@Composable
private fun PreviewStatisticScreen() {
    BlueSenseTheme {
        Surface {
//            StatisticScreen()
        }
    }
}