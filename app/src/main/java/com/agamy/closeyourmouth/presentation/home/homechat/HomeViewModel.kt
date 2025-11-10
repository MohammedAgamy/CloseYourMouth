package com.agamy.closeyourmouth.presentation.home.homechat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agamy.closeyourmouth.domain.usecase.GetUserChatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserChatsUseCase: GetUserChatsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private val _intent = Channel<HomeIntent>()
    val intent = _intent.receiveAsFlow()

    init {
        handleIntents()
    }

    fun sendIntent(intent: HomeIntent) {
        viewModelScope.launch { _intent.send(intent) }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intent.collect { action ->
                when (action) {
                    is HomeIntent.LoadChats -> loadChats(action.userId)

                }
            }
        }
    }

    private fun loadChats(userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val chats = getUserChatsUseCase(userId)
                _state.value = HomeState(chats = chats)
            } catch (e: Exception) {
                _state.value = HomeState(error = e.message)
            }
        }
    }
}