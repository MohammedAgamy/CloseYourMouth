package com.agamy.closeyourmouth.presentation.auth.login.phone

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agamy.closeyourmouth.domain.usecase.SendOtpUseCase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneNumberViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase

) : ViewModel() {
    private val _state = MutableStateFlow<PhoneNumberState>(PhoneNumberState.Idle)
    val state: StateFlow<PhoneNumberState> = _state

    fun handleIntent(intent: PhoneNumberIntent) {
        when (intent) {
            is PhoneNumberIntent.SendCode -> sendCode(intent.phoneNumber, intent.activity)
        }
    }

    private fun sendCode(phoneNumber: String, activity: Activity) {
        _state.value = PhoneNumberState.Loading

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                _state.value = PhoneNumberState.SuccessAuto(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                _state.value = PhoneNumberState.Error(e.message ?: "Verification failed")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                _state.value = PhoneNumberState.CodeSent(verificationId)
            }
        }

        viewModelScope.launch {
            sendOtpUseCase(phoneNumber, activity, callbacks)
        }
    }
}