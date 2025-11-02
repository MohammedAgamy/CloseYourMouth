package com.agamy.closeyourmouth.presentation.auth.login.otp

import com.google.firebase.auth.FirebaseUser

sealed class OtpState {
    object Idle : OtpState()
    object Loading : OtpState()
    object Verified : OtpState()
    data class Error(val message: String) : OtpState()
}