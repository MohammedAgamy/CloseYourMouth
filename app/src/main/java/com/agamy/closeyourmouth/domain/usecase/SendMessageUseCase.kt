package com.agamy.closeyourmouth.domain.usecase

import com.agamy.closeyourmouth.data.model.Message
import com.agamy.closeyourmouth.data.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: ChatRepository
) {

    suspend operator fun invoke(chatId: String, message: Message) {
        repository.sendMessage(chatId, message)
    }
}