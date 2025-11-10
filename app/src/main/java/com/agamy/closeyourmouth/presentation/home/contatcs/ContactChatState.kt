package com.agamy.closeyourmouth.presentation.home.contatcs

import com.agamy.closeyourmouth.data.model.User

data class ContactChatState (
    val contacts: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)