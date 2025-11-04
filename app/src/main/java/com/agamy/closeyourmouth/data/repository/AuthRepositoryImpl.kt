package com.agamy.closeyourmouth.data.repository

import android.app.Activity
import com.agamy.closeyourmouth.data.remote.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

// AuthRepositoryImpl provides concrete implementations for the AuthRepository interface,
// handling authentication operations using Firebase Authentication.
// It uses FirebaseAuth to send verification codes and verify them.
// The class is annotated with @Inject to enable dependency injection with Hilt.
//
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {
    override suspend fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {

        // Configure the phone authentication options
        // using the provided phone number, activity, and callbacks.
        // The timeout is set to 60 seconds.
        // Finally, initiate the phone number verification process.
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // Verifies the provided code against the verification ID.
    // It creates a credential using the verification ID and code,
    // then signs in with that credential and returns the authenticated FirebaseUser.
    override suspend fun verifyCode(
        verificationId: String,
        code: String
    ): FirebaseUser? {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        return auth.signInWithCredential(credential).await().user
    }


}