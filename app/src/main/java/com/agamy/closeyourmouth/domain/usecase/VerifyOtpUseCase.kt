package com.agamy.closeyourmouth.domain.usecase

import com.agamy.closeyourmouth.data.remote.AuthRepository
import javax.inject.Inject

// VerifyOtpUseCase handles the verification of OTP codes.
class VerifyOtpUseCase @Inject constructor(
    private val repository: AuthRepository

) {
    // Invokes the use case to verify the provided code against the verification ID.
    // Parameters:
    // - verificationId: The ID received when the code was sent.
    // - code: The verification code entered by the user.
    // This function is suspendable, indicating it performs asynchronous operations.
    suspend operator fun invoke(
        verificationId: String,
        code: String
    ) = repository.verifyCode(verificationId, code)


}
