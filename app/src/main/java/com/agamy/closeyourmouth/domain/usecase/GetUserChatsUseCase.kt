package com.agamy.closeyourmouth.domain.usecase

import com.agamy.closeyourmouth.data.model.ChatItem
import com.agamy.closeyourmouth.data.repository.HomeRepository
import javax.inject.Inject

class GetUserChatsUseCase  @Inject constructor(
    private val repository: HomeRepository
) {

    suspend operator fun invoke(currentUserId: String): List<ChatItem> {
        return repository.getUserChat(currentUserId)
    }
}