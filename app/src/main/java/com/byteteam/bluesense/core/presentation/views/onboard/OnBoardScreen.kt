package com.byteteam.bluesense.core.presentation.views.onboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.views.onboard.common.OnBoardPageContent
import com.byteteam.bluesense.core.presentation.views.onboard.widgets.DotButtons
import com.byteteam.bluesense.core.presentation.views.onboard.widgets.OnBoardContent
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardScreen(modifier: Modifier = Modifier){
    val onBoardPageContents = listOf(
        OnBoardPageContent(
            imagePainter = painterResource(id = R.drawable.water_drop_rafiki_1), desc ="page-1", textBody =  "Bluesense akan memberikan informasi real-time untuk memastikan air di sekitarmu selalu bersih dan aman.", title = "Mulai deteksi kualitas air secara langsung!"
        ), OnBoardPageContent(
            imagePainter = painterResource(id = R.drawable.customer_survey_rafiki_1), desc ="page-2", textBody =  "Bluesense memberikan histori yang jelas dan mudah dimengerti untuk membantu kamu membuat keputusan yang lebih baik.", title = "Jelajahi riwayat kualitas air di sekitarmu!"
        ), OnBoardPageContent(
            imagePainter = painterResource(id = R.drawable.push_notifications_rafiki_1), desc ="page-3", textBody =  "Bluesense akan memberi tahu Anda secara langsung ketika kualitas air mencapai tingkat yang tidak diinginkan, memberi Anda kendali penuh atas kesehatan air di rumah.", title = "Tetap terinformasi!"
        ), OnBoardPageContent(
            imagePainter = painterResource(id = R.drawable.light_bulb_bro_1), desc ="page-4", textBody =  "Dapatkan rekomendasi alat pembersih air terbaik untuk menjaga kualitas air di rumahmu.", title = "Deteksi masalah, berikan solusi!!"
        ),
    )
    val pagerState = rememberPagerState(pageCount = {
        onBoardPageContents.size
    })
    val coroutineScope = rememberCoroutineScope()
    var currentPageIndex by remember{ mutableIntStateOf(0) }

    fun nextPage(){
        coroutineScope.launch {
            // Call scroll to on pagerState
            if(currentPageIndex < onBoardPageContents.size) {
                currentPageIndex += 1
                pagerState.animateScrollToPage(currentPageIndex)
            }
        }
    }

    Box(modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState) { page ->
            // Our page content
            OnBoardContent(onBoardPageContents[page])
        }
        DotButtons(activeIndex = currentPageIndex, callbackOnTap = {index ->  coroutineScope.launch {
            // Call scroll to on pagerState
            currentPageIndex = index
            pagerState.animateScrollToPage(index)
        }},  modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 116.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 36.dp, start = 24.dp, end = 24.dp)) {
            Text(text = "Lewati")
            Text(text = "Lanjut", modifier = Modifier.clickable { nextPage() })
        }
    }
}




@Preview
@Composable
private fun Preview(){
    BlueSenseTheme {
        Surface {
            OnBoardScreen()
        }
    }
}