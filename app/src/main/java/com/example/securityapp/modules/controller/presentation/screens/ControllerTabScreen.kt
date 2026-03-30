package com.example.securityapp.modules.controller.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.securityapp.app.sharedHiltViewModel
import com.example.securityapp.modules.messages.presentation.MessagesScreen
import com.example.securityapp.core.presentation.TabComponent
import com.example.securityapp.modules.controller.presentation.models.ControllerActionsState
import com.example.securityapp.modules.controller.presentation.models.ControllerTabAction
import com.example.securityapp.modules.controller.presentation.models.ControllerTabEvent
import com.example.securityapp.modules.controller.presentation.vm.ControllerActionsVm
import com.example.securityapp.modules.controller.presentation.vm.ControllerCommonVm
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ControllerTabScreenRoot(navController: NavHostController, entry: NavBackStackEntry) {
    val commonControllerCommonVm = entry.sharedHiltViewModel<ControllerCommonVm>(navController)
    val commonState=  commonControllerCommonVm.state.collectAsStateWithLifecycle()
    val vm = hiltViewModel<ControllerActionsVm>()
    val state = vm.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    when(commonState.value){
        null-> Unit
        else -> vm.onNumberReceived(commonControllerCommonVm.selectedController)
    }
    LaunchedEffect(Unit) {
        vm.events.collectLatest {
            when(it){
                is ControllerTabEvent.Toast -> Toast.makeText(context, it.str, Toast.LENGTH_SHORT).show()
            }
        }
    }
    ControllerTabScreen(
        state = state.value
    ) {
        vm.onAction(it)
    }
}
@Composable
fun ControllerTabScreen(
    state: ControllerActionsState,
    onAction: (ControllerTabAction) -> Unit
) {
    val pagerState = rememberPagerState { 2 }

    LaunchedEffect(state.selectedTab) {
        pagerState.animateScrollToPage(state.selectedTab)
    }
    LaunchedEffect(pagerState.currentPage) {
        onAction(ControllerTabAction.OnTabSelected(pagerState.currentPage))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.safeContent.only(
                    WindowInsetsSides.Vertical
                )
            )
    ) {
        when (state.isLoading) {
            true -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            false -> Unit
        }
        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(
                selectedTabIndex = state.selectedTab,
                modifier = Modifier.fillMaxWidth(),
            ) {
                TabComponent(
                    isSelected = state.selectedTab == 0,
                    onClick = {
                        onAction(ControllerTabAction.OnTabSelected(0))
                    },
                    text = "Actions"
                )
                TabComponent(
                    isSelected = state.selectedTab == 1,
                    onClick = {
                        onAction(ControllerTabAction.OnTabSelected(1))
                    },
                    text = "Messages"
                )
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .background(color = Color.Black)
                    .fillMaxWidth()
                    .weight(1f)
            ) { pagerState ->
                when (pagerState) {
                    0 -> ControllerActionsScreen(state.number, onAction)
                    1 -> MessagesScreen(state.messages)
                }
            }
        }
    }
}
