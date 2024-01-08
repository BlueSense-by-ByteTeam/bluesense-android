package com.byteteam.bluesense.core.presentation.views.statistic.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

@Composable
fun ChartTemplate(chartEntryModelProducer: ChartEntryModelProducer, modifier: Modifier = Modifier){
    ProvideChartStyle(chartStyle = ChartStyle.fromColors(
        axisGuidelineColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        axisLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        axisLineColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0f),
        elevationOverlayColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        entityColors = listOf(MaterialTheme.colorScheme.primary),
    )) {
        Chart(
            modifier = modifier,
            chart = columnChart(),
            chartModelProducer = chartEntryModelProducer,
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(),
        )
    }
}