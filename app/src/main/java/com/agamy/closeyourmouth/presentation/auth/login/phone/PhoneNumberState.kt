package com.agamy.closeyourmouth.presentation.auth.login.phone

import com.google.firebase.auth.PhoneAuthCredential

sealed class PhoneNumberState {
    object Idle : PhoneNumberState()
    object Loading : PhoneNumberState()
    data class CodeSent(val verificationId: String) : PhoneNumberState()
    data class SuccessAuto(val credential: PhoneAuthCredential) : PhoneNumberState()
    data class Error(val message: String) : PhoneNumberState()
}