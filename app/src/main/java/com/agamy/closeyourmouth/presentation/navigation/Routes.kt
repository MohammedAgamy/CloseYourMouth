package com.agamy.closeyourmouth.presentation.navigation

object Routes {
    const val LOGIN = "login"
    const val SPLASH = "splash"
    const val OTP = "otp/{verificationId}"

    fun otp(verificationId: String) = "otp/$verificationId"
    const val Home = "home"
}