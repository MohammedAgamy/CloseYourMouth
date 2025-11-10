package com.agamy.closeyourmouth.domain.usecase

import android.app.Activity
import com.agamy.closeyourmouth.data.remote.AuthRepository
import com.google.firebase.auth.PhoneAuthProvider
import javax.inject.Inject

class SendOtpUseCase @Inject constructor(
    private val repository: AuthRepository,

) {
// Invokes the use case to send a verification code to the specified phone number.
    // Parameters:
    // - phoneNumber: The phone number to which the verification code will be sent.
    // - activity: The activity context required for phone number verification.
    // - callbacks: Callbacks to handle verification state changes.
    // This function is suspendable, indicating it performs asynchronous operations.
    suspend operator fun invoke(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) = repository.sendVerificationCode(phoneNumber, activity, callbacks)

}