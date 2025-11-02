package com.agamy.closeyourmouth.data.remote

import android.app.Activity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider

interface AuthRepository {

    suspend fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )

    suspend fun verifyCode(verificationId: String, code: String): FirebaseUser?
}