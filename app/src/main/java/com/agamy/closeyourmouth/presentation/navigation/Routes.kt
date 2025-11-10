package com.agamy.closeyourmouth.presentation.navigation

object Routes {
    const val LOGIN = "login"
    const val SPLASH = "splash"
    const val OTP = "otp/{verificationId}"

    fun otp(verificationId: String) = "otp/$verificationId"
    const val HomeContainer = "homecontainer"
    const val Contacts = "contacts"
    const val MORE = "more"

    const val HomeScreen = "homescreen"
    const val CHAT = "chat/{receiverId}/{receiverName}"


    fun createChatRoute(chatId: String, receiverId: String, receiverName: String) =
        "chat/$chatId/$receiverId/$receiverName"

}

