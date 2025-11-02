package com.agamy.closeyourmouth.domain.usecase

import com.agamy.closeyourmouth.data.remote.AuthRepository
import javax.inject.Inject


class VerifyOtpUseCase @Inject constructor(
    private val repository: AuthRepository

) {
    suspend operator fun invoke(
        verificationId: String,
        code: String
    ) = repository.verifyCode(verificationId, code)


}
