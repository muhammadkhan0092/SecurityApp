package com.example.securityapp.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.securityapp.core.domain.MessageTypeFromControlled
import com.example.securityapp.modules.controller.domain.models.MessagesDomain

@Composable
fun MessagesScreen(
    messages: List<MessagesDomain>
) {
    when(messages.isEmpty()){
        true -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
               Text("No Messages")
            }
        }
        false -> {
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(messages) {
                    MessageItem(it)
                }
            }
        }
    }
}
@Composable
fun MessageItem(
    data : MessagesDomain
){
    val color = when(data.type){
        MessageTypeFromControlled.NORMAL -> Color.Black
        MessageTypeFromControlled.ERROR -> Color.Red
    }
    Text(
        text = data.message,
        color = color,
        maxLines = 3
    )
}