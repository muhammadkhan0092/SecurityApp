package com.example.securityapp.modules.controller.presentation.screens

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
import com.example.securityapp.core.presentation.MessagesScreen
import com.example.securityapp.core.TabComponent
import com.example.securityapp.modules.controller.presentation.models.ControllerActionsState
import com.example.securityapp.modules.controller.presentation.models.ControllerTabAction

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
                    .fillMaxWidth()
                    .weight(1f)
            ) { pagerState ->
                when (pagerState) {
                    0 -> {
                        ControllerActionsScreen(state.number, onAction)
                    }

                    1 -> {
                        MessagesScreen(state.messages)
                    }
                }
            }
        }
    }
}
