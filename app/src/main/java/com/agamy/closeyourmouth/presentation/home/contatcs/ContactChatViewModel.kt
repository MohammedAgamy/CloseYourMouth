package com.agamy.closeyourmouth.presentation.home.contatcs

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agamy.closeyourmouth.data.model.ContactItem
import com.agamy.closeyourmouth.data.model.User
import com.agamy.closeyourmouth.domain.usecase.GetAppContactsUseCase
import com.agamy.closeyourmouth.domain.usecase.GetDeviceContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactChatViewModel @Inject constructor(
    private val getAppContactsUseCase: GetAppContactsUseCase,
    private val getDeviceContactsUseCase: GetDeviceContactsUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(ContactChatState())
    val state: StateFlow<ContactChatState> = _state
    fun loadContacts() {
        viewModelScope.launch {
            _state.value = ContactChatState(isLoading = true)

            try {
                // 1️⃣ جلب الكونتاكتس من الموبايل
                val deviceContacts: List<ContactItem> = getDeviceContactsUseCase()

                // 2️⃣ جلب المستخدمين المسجلين في التطبيق ومطابقتهم بالكونتاكتس
                val appUsers: List<User> = getAppContactsUseCase(deviceContacts)

                // 3️⃣ تحديث ال state
                _state.value = ContactChatState(
                    contacts = appUsers,
                    isLoading = false
                )

            } catch (e: Exception) {
                _state.value = ContactChatState(
                    contacts = emptyList(),
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}