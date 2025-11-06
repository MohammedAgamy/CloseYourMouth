package com.agamy.closeyourmouth.presentation.home.homechat.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agamy.closeyourmouth.data.model.Message
import com.agamy.closeyourmouth.domain.usecase.ObserveMessagesUseCase
import com.agamy.closeyourmouth.domain.usecase.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val observeMessages: ObserveMessagesUseCase,
    private val sendMessage: SendMessageUseCase
) : ViewModel() {



    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state

    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.MessageTextChanged -> {
                _state.value = _state.value.copy(messageText = event.text)
            }

            is ChatEvent.SendMessage -> {
                viewModelScope.launch {
                    val message = Message(
                        senderId = event.senderId,
                        receiverId = event.receiverId,
                        message = _state.value.messageText
                    )
                    sendMessage(event.chatId, message)
                    _state.value = _state.value.copy(messageText = "")
                }
            }

            is ChatEvent.LoadMessages -> {
                observeMessages(event.chatId)
                    .onEach { messages ->
                        _state.value = _state.value.copy(messages = messages)
                    }

                    .launchIn(viewModelScope)
            }
        }
    }
}
