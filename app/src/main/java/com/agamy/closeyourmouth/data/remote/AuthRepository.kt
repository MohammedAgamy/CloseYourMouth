package com.agamy.closeyourmouth.data.remote

import android.app.Activity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider


//  AuthRepository defines the contract for authentication-related operations.
interface AuthRepository {

    // Sends a verification code to the specified phone number.
    // This function is suspendable, indicating it performs asynchronous operations.
    // Parameters:
    // - phoneNumber: The phone number to which the verification code will be sent.
    // - activity: The activity context required for phone number verification.
    // - callbacks: Callbacks to handle verification state changes.
    suspend fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )

    // Verifies the provided code against the verification ID.
    // This function is suspendable, indicating it performs asynchronous operations.
    // Parameters:
    // - verificationId: The ID received when the code was sent.
    // - code: The verification code entered by the user.
    // Returns:
    // - FirebaseUser if the verification is successful, null otherwise.

    suspend fun verifyCode(verificationId: String, code: String): FirebaseUser?
}