package com.byteteam.bluesense.core.presentation.views.statistic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.tokens.SortData
import com.byteteam.bluesense.core.presentation.tokens.SortDateLog
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.ChartTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.DescTextTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.OptionSortDateTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.OptionStatTemplate
import com.byteteam.bluesense.core.presentation.views.statistic.widgets.StatsTextTemplate
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticScreen(modifier: Modifier = Modifier) {
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

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.history_water_quality)) })
        }
    ) { padding ->
        Column(
            modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            OptionSortDateTemplate(sortDates = sortDates, selectedDate = selectedDate, onClick = { selectedDate = it })
            ChartTemplate(chartEntryModelProducer = chartEntryModelProducer)
            OptionStatTemplate(sortDatas = sortDatas, selectedData = selectedData, onClick = { selectedData = it })
            StatsTextTemplate()
            DescTextTemplate()
        }
    }
}

fun getRandomEntries() = List(4) { entryOf(it, Random.nextFloat() * 16f) }


@Preview
@Composable
private fun PreviewStatisticScreen() {
    BlueSenseTheme {
        Surface {
            StatisticScreen()
        }
    }
}