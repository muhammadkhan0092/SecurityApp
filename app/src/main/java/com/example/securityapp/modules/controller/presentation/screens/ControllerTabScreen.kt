package com.example.securityapp.modules.controller.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.securityapp.core.presentation.ButtonComposable
import com.example.securityapp.modules.controller.domain.ControllerMessagesDomain
import com.example.securityapp.modules.controller.presentation.components.ControllerMessagesItem
import com.example.securityapp.modules.controller.presentation.models.ControllerActionsState
import com.example.securityapp.modules.controller.presentation.models.ControllerTabAction

@Composable
fun ControllerTabScreen(
    state: ControllerActionsState,
    onAction : (ControllerTabAction)-> Unit
) {
    val pagerState = rememberPagerState{ 2 }

    LaunchedEffect(state.selectedTab) {
        pagerState.animateScrollToPage(state.selectedTab)
    }
    LaunchedEffect(pagerState.currentPage) {
        onAction(ControllerTabAction.OnTabSelected(pagerState.currentPage))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
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
                Tab(
                    selected = state.selectedTab==0,
                    onClick = {
                        onAction(ControllerTabAction.OnTabSelected(0))
                    },
                    selectedContentColor = Color.Yellow,
                    unselectedContentColor = Color.Transparent
                ) {
                    Text("Actions")
                }
                Tab(
                    selected = state.selectedTab==1,
                    onClick = {
                        onAction(ControllerTabAction.OnTabSelected(1))
                    },
                    selectedContentColor = Color.Yellow,
                    unselectedContentColor = Color.Transparent
                ) {
                    Text("Messages")
                }
            }
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth().weight(1f)){pagerState->
                when (pagerState) {
                    0 -> {
                        ControllerActions(state.numbers)
                    }

                    1 -> {
                        ControllerMessages(state.messages)
                    }
                }
            }
        }
    }
}

@Composable
fun ControllerMessages(messages: List<ControllerMessagesDomain>) {
    LazyColumn {
        items(messages) {
            ControllerMessagesItem(it)
        }
    }
}

@Composable
fun ControllerActions(numbers: List<String>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        NumbersComposable(numbers = numbers)
        ButtonComposable("Wipe Data") {

        }
        ButtonComposable(text = "Clear Gallery") {

        }
        ButtonComposable(text = "Fetch Location") {

        }
        ButtonComposable(text = "Block Apps") {

        }
    }
}

@Composable
fun NumbersComposable(modifier: Modifier = Modifier, numbers: List<String>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Ph No : ")
        Column {
            numbers.forEach {
                Text(it)
            }
        }
    }
}