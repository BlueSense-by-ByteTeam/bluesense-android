package com.byteteam.bluesense.core.presentation.views.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.LogHistoryEntity
import com.byteteam.bluesense.core.presentation.tokens.SortData
import com.byteteam.bluesense.core.presentation.tokens.SortDateLog
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.ChartTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.DescTextTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.Legend
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.LegendTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.OptionSortDateTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.OptionStatTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.StatsTextTemplate
import com.byteteam.bluesense.core.presentation.widgets.ErrorHandler
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
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
    val chartEntryModelProducer = ChartEntryModelProducer(getRandomEntries())

    var isFetchingData by remember { mutableStateOf(false) }
    fun fetchOnce() {
        if (isFetchingData) {
            isFetchingData = false
            getHistoryToday()
        }
    }
//    var entries = ChartEntryModelProducer()
    LaunchedEffect(selectedDate) {
        when (selectedDate) {
            SortDateLog.TODAY -> getHistoryToday()
            SortDateLog.WEEK -> getHistoryWeek()
            SortDateLog.MONTH -> getHistoryMonth()
            SortDateLog.YEAR -> getHistoryYear()
            else -> getHistoryToday()
        }
    }

    historyState.collectAsState().value.let {
        when (it) {
            is Resource.Loading -> {
                isFetchingData = true
                Box(modifier = Modifier.size(24.dp)) {
                    CircularProgressIndicator()
                }
            }

            is Resource.Error -> {
                isFetchingData = false
                ErrorHandler(
                    modifier = Modifier.fillMaxSize(),
                    onPressRetry = { getHistoryToday() },
                    errorText = it.message ?: "Error"
                )
            }

            is Resource.Success -> {
                isFetchingData = false
//                val entries = it.data?.logs?.mapIndexed { index, logEntity ->
//                    entryOf(index, logEntity.ph)
//                } ?: listOf()

                val entries = when (selectedData) {
                    SortData.PH -> it.data?.logs?.mapIndexed { index, logEntity ->
                        entryOf(index, logEntity.ph)
                    }

                    SortData.TDS -> it.data?.logs?.mapIndexed { index, logEntity ->
                        entryOf(index, logEntity.tds)
                    }

                    SortData.QUALITY -> it.data?.logs?.mapIndexed { index, logEntity ->
                        entryOf(index, if (logEntity.quality == 0) 1 else 2)
                    }

                    SortData.STATUS -> it.data?.logs?.mapIndexed { index, logEntity ->
                        entryOf(index, if (logEntity.status == 0) 1 else 2)
                    }

                    else -> it.data?.logs?.mapIndexed { index, logEntity ->
                        entryOf(index, logEntity.ph)
                    }
                } ?: listOf()

                Column(
                    modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    OptionSortDateTemplate(
                        sortDates = sortDates,
                        selectedDate = selectedDate,
                        onClick = { selectedDate = it })
                    ChartTemplate(chartEntryModelProducer = ChartEntryModelProducer(entries))
                    if (selectedData == SortData.STATUS || selectedData == SortData.QUALITY) LegendTemplate()
                    OptionStatTemplate(
                        sortDatas = sortDatas,
                        selectedData = selectedData,
                        onClick = { selectedData = it })
                    StatsTextTemplate()
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
        }
    }
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