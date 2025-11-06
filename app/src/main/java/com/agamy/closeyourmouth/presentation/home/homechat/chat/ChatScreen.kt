package com.agamy.closeyourmouth.presentation.home.homechat.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ChatScreen(
    chatId: String,
    senderId: String,
    receiverId: String,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(ChatEvent.LoadMessages(chatId))
    }

    Column(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(state.messages) { message ->
                Text(
                    text = "${message.senderId}: ${message.message}",
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            TextField(
                value = state.messageText,
                onValueChange = { viewModel.onEvent(ChatEvent.MessageTextChanged(it)) },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type message...") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                viewModel.onEvent(
                    ChatEvent.SendMessage(chatId, senderId, receiverId)
                )
            }) {
                Text("Send")
            }
        }
    }
}