package com.example.securityapp.modules.messages.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.securityapp.modules.messages.domain.models.MessageTypeFromControlled
import com.example.securityapp.modules.messages.domain.models.MessagesDomain
import com.example.securityapp.ui.theme.Purple40

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
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(10.dp)
            ){
                items(messages) {
                    MessageItem(it)
                    Spacer(modifier = Modifier.height(10.dp))
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
    Box(
        modifier = Modifier
            .background(Purple40.copy(0.4f), RoundedCornerShape(10.dp))
            .padding(10.dp)
    ){
        Text(
            text = data.message,
            color = color,
            maxLines = 3
        )
    }
}