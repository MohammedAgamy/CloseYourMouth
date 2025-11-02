package com.agamy.closeyourmouth.presentation.auth.login.otp

sealed class OtpIntent {
    data class VerifyCode(val verificationId: String, val code: String) : OtpIntent()

}