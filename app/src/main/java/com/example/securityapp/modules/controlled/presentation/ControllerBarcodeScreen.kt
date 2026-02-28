package com.example.securityapp.modules.controlled.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun ControlledHomeScreen(
    state: ControlledState,
    onAction: (ControlledAction) -> Unit
) {

    val pagerState = rememberPagerState { 3 }
    LaunchedEffect(pagerState.currentPage) {
        onAction(ControlledAction.OnTabSelected(pagerState.currentPage))
    }
    LaunchedEffect(state.selectedIndex) {
        pagerState.animateScrollToPage(state.selectedIndex)
    }
    Column (modifier = Modifier
        .fillMaxSize()
        .safeContentPadding()) {
        TabRow(selectedTabIndex = state.selectedIndex) {
            Tab(selected = state.selectedIndex==0, onClick = {
                onAction(ControlledAction.OnTabSelected(0))
            }) {
                Text("Controllers")
            }
            Tab(selected = state.selectedIndex==0, onClick = {
                onAction(ControlledAction.OnTabSelected(1))
            }) {
                Text("Messages")
            }
            Tab(selected = state.selectedIndex==0, onClick = {
                onAction(ControlledAction.OnTabSelected(2))
            }) {
                Text("Barcode")
            }
        }
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth().weight(1f)) { pagerState->
            when (pagerState) {
                0 -> ControllerComposable()
                1-> MessagesComposable()
                2-> BarcodeComposable(bitmap = state.bitmap)
            }
        }
    }
}

@Composable
fun BarcodeComposable(bitmap: Bitmap?) {
    Log.d("KHAN","BITMAP IS $bitmap")
    when(bitmap){
        null->{
            Box(modifier = Modifier.fillMaxSize().safeContentPadding()){
                CircularProgressIndicator()
            }
        }
        else -> {
            val imageBitmap = bitmap.asImageBitmap()
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Log.d("KHAN", "IN CONTROLLED BARCODE SCREEN")
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Device Barcode",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}


@Composable
fun MessagesComposable() {
    Text("In Messages")
}

@Composable
fun ControllerComposable() {
    Text("In Controller Composable")
}