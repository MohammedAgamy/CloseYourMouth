package com.agamy.closeyourmouth.presentation.home.homechat.chat

sealed class ChatEvent {
    data class MessageTextChanged(val text: String) : ChatEvent()
    data class SendMessage(val chatId: String, val senderId: String, val receiverId: String) : ChatEvent()
    data class LoadMessages(val chatId: String) : ChatEvent()
}