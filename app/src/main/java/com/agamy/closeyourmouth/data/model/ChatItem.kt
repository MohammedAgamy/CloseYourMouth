package com.agamy.closeyourmouth.data.model

data class ChatItem(
    val chatId: String = "",
    val otherUserId: String = "",
    val otherUserName: String = "",
    val lastMessage: String = "",
    val lastMessageTime: Long = 0L,
    val otherUserImage: String = ""
)
