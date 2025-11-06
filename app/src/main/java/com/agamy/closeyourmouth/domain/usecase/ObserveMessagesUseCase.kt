package com.agamy.closeyourmouth.domain.usecase

import com.agamy.closeyourmouth.data.model.Message
import com.agamy.closeyourmouth.data.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMessagesUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    operator fun invoke(chatId: String): Flow<List<Message>> {
        return repository.observeMessages(chatId)
    }
}