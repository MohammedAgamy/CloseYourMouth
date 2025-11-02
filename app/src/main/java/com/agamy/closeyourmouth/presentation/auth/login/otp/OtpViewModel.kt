package com.agamy.closeyourmouth.presentation.auth.login.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agamy.closeyourmouth.data.remote.AuthRepository
import com.agamy.closeyourmouth.domain.usecase.VerifyOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val verifyOtpUseCase: VerifyOtpUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<OtpState>(OtpState.Idle)
    val state: StateFlow<OtpState> = _state

    fun handleIntent(intent: OtpIntent) {
        when (intent) {
            is OtpIntent.VerifyCode -> verifyCode(intent.verificationId, intent.code)
        }
    }

    private fun verifyCode(verificationId: String, code: String) {
        _state.value = OtpState.Loading
        viewModelScope.launch {
            try {
                val user = verifyOtpUseCase(verificationId, code)
                if (user != null) _state.value = OtpState.Verified
                else _state.value = OtpState.Error("Verification failed")
            } catch (e: Exception) {
                _state.value = OtpState.Error(e.message ?: "Error")
            }
        }
    }
}