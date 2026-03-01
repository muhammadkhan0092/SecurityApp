package com.example.securityapp.modules.controlled.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.securityapp.core.presentation.MessagesScreen
import com.example.securityapp.modules.controlled.presentation.models.ControlledAction
import com.example.securityapp.modules.controlled.presentation.models.ControlledState

@Composable
fun ControlledTabs(
    state: ControlledState,
    onAction: (ControlledAction) -> Unit
) {

    val pagerState = rememberPagerState { 3 }
    LaunchedEffect(pagerState.currentPage) {
        onAction(ControlledAction.OnTabSelected(pagerState.currentPage))
    }
    LaunchedEffect(state.selectedIndex) {
        if (pagerState.currentPage != state.selectedIndex) {
            pagerState.scrollToPage(state.selectedIndex)
        }
    }
    Column (
        modifier = Modifier
        .fillMaxSize()
        .safeContentPadding()
    ) {
        TabRow(
            selectedTabIndex = state.selectedIndex,
            containerColor = Color.White,
            contentColor = Color.Blue
            ) {
            Tab(
                selected = state.selectedIndex==0,
                onClick = {
                    onAction(ControlledAction.OnTabSelected(0))
                }
            ) {
                Text(
                    "Controllers",
                    modifier = Modifier.padding(vertical = 30.dp)
                )
            }
            Tab(
                selected = state.selectedIndex==0,
                onClick = {
                onAction(ControlledAction.OnTabSelected(1))
                }
            ) {
                Text(
                    "Messages", modifier = Modifier.padding(vertical = 30.dp)
                )
            }
            Tab(selected = state.selectedIndex==0, onClick = {
                onAction(ControlledAction.OnTabSelected(2))
            }) {
                Text(
                    "Barcode", modifier = Modifier.padding(vertical = 30.dp)
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { pagerState->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                when (pagerState) {
                    0 -> ControllerListComposable(state.controllers)
                    1-> MessagesScreen(state.messages)
                    2-> BarcodeComposable(bitmap = state.bitmap)
                }
            }
        }
    }
}
