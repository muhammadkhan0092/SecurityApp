package com.example.securityapp.modules.controlled.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.securityapp.core.presentation.MessagesScreen
import com.example.securityapp.modules.controlled.presentation.models.ControlledAction
import com.example.securityapp.modules.controlled.presentation.models.ControlledEvents
import com.example.securityapp.modules.controlled.presentation.models.ControlledState
import com.example.securityapp.core.presentation.TabComponent
import com.example.securityapp.modules.controlled.presentation.vm.ControlledBarcodeVm
import com.example.securityapp.ui.theme.Purple40
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ControlledTabsRoot() {
    val vm = hiltViewModel<ControlledBarcodeVm>()
    val state by vm.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val pagerState = rememberPagerState { 3 }
    LaunchedEffect(pagerState.currentPage) {
        vm.onAction(ControlledAction.OnTabSelected(pagerState.currentPage))
    }
    LaunchedEffect(state.selectedIndex) {
        if (pagerState.currentPage != state.selectedIndex) {
            pagerState.scrollToPage(state.selectedIndex)
        }
    }
    LaunchedEffect(Unit) {
        vm.events.collectLatest {
            when(it){
                is ControlledEvents.Toast -> Toast.makeText(context, it.str, Toast.LENGTH_SHORT).show()
            }
        }
    }
    ControlledTabs(
        state = state,
        pagerState
    ) {
        vm.onAction(it)
    }
}
@Composable
fun ControlledTabs(
    state: ControlledState,
    pagerState: PagerState,
    onAction: (ControlledAction) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {
        TabRow(
            selectedTabIndex = state.selectedIndex,
            containerColor = Color.White,
            contentColor = Color.Blue,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[state.selectedIndex]),
                    color = Purple40
                )
            }
            ) {
            TabComponent(
                isSelected = state.selectedIndex==0,
                onClick = {
                    onAction(ControlledAction.OnTabSelected(0))
                },
                text = "Controllers"
            )
            TabComponent(
                isSelected = state.selectedIndex==1,
                onClick = {
                    onAction(ControlledAction.OnTabSelected(1))
                },
                text = "Messages"
            )
            TabComponent(
                isSelected = state.selectedIndex==2,
                onClick = {
                    onAction(ControlledAction.OnTabSelected(2))
                },
                text = "Barcode"
            )
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
                    0 -> ControllerListComposable(state.controllers,onAction)
                    1-> MessagesScreen(state.messages)
                    2-> BarcodeComposable(bitmap = state.bitmap)
                }
            }
        }
    }
}
