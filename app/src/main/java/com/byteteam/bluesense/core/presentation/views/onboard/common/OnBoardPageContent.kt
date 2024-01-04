package com.byteteam.bluesense.core.presentation.views.onboard.common

import androidx.compose.ui.graphics.painter.Painter

data class OnBoardPageContent(
    val imagePainter: Painter,
    val desc: String,
    val title: String,
    val textBody: String,
)
