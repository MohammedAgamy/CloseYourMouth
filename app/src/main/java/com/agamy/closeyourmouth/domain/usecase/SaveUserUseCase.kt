package com.agamy.closeyourmouth.domain.usecase

import com.agamy.closeyourmouth.data.model.SaveUser
import com.agamy.closeyourmouth.data.repository.SaveUserRepository
import javax.inject.Inject

class SaveUserUseCase  @Inject constructor(
    private val repository: SaveUserRepository
) {
    suspend operator fun invoke(user: SaveUser) {
        repository.saveUserToFirestore(user)
    }
}