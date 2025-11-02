package com.agamy.closeyourmouth.domain.usecase

import android.app.Activity
import com.agamy.closeyourmouth.data.remote.AuthRepository
import com.google.firebase.auth.PhoneAuthProvider
import javax.inject.Inject

class SendOtpUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) = repository.sendVerificationCode(phoneNumber, activity, callbacks)

}