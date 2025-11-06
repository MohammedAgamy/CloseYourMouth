package com.agamy.closeyourmouth.presentation.home.homechat.chat

import com.agamy.closeyourmouth.data.model.Message

data class ChatState(val messages: List<Message> = emptyList(),
                     val messageText: String = "",
                     val isLoading: Boolean = false,
                     val error: String? = null)
