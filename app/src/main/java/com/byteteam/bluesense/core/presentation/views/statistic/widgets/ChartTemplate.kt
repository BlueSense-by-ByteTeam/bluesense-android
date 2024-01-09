package com.byteteam.bluesense.core.presentation.views.statistic.widgets

import android.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.overlayingComponent
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.component.Component
import com.patrykandpatrick.vico.core.component.OverlayingComponent
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.ComponentShader
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.extension.copyColor

@Composable
fun ChartTemplate(chartEntryModelProducer: ChartEntryModelProducer, modifier: Modifier = Modifier){
    val shape = ShapeComponent(
        color = Color.BLACK,
        dynamicShader = ComponentShader(ShapeComponent(Shapes.pillShape, Color.DKGRAY), componentSizeDp = 4f),
        strokeWidthDp = 2f,
        strokeColor = Color.BLACK,
    )

    val overLayingComponent = OverlayingComponent(
        outer = ShapeComponent(Shapes.pillShape, Color.BLACK.copyColor(alpha = .32f)),
        innerPaddingAllDp = 10f,
        inner = OverlayingComponent(
            outer = ShapeComponent(Shapes.pillShape, Color.BLACK),
            inner = ShapeComponent(Shapes.pillShape, Color.LTGRAY),
            innerPaddingAllDp = 5f,
        ),
    )

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
            marker = MarkerComponent(TextComponent.Builder().build(),  overlayingComponent(
                outer = shape,
                inner = overLayingComponent,
                innerPaddingAll = 12.dp,
            ), null),
        )
    }
}