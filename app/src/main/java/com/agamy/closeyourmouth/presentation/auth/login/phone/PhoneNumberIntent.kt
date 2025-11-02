package com.agamy.closeyourmouth.presentation.auth.login.phone

import android.app.Activity

sealed class PhoneNumberIntent {
    data class SendCode(val phoneNumber: String, val activity: Activity) : PhoneNumberIntent()

}