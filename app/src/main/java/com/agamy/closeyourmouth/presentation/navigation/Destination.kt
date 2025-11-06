package com.agamy.closeyourmouth.presentation.navigation

import com.agamy.closeyourmouth.R

data class Destination(
    val route: String,
    val icon: Int,
    val title: String
)


val bottomNavItems = listOf(
    Destination(Routes.Contacts, icon = R.drawable.adduser, "Contacts"),
    Destination(Routes.CHAT, icon = R.drawable.chat, "Chat"),
    Destination(Routes.MORE, icon = R.drawable.more, "More"),

)