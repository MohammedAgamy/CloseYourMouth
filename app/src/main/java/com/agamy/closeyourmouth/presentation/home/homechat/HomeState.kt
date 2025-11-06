package com.agamy.closeyourmouth.presentation.home.homechat

import com.agamy.closeyourmouth.data.model.ChatItem

data class HomeState(val isLoading: Boolean = false,
                     val chats: List<ChatItem> = emptyList(),
                     val error: String? = null)
