package com.agamy.closeyourmouth.domain.usecase

import android.content.Context
import android.provider.ContactsContract
import com.agamy.closeyourmouth.data.model.ContactItem
import com.agamy.closeyourmouth.data.repository.NewChatRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDeviceContactsUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(): List<ContactItem> = withContext(Dispatchers.IO) {
        val contactList = mutableListOf<ContactItem>()
        val contentResolver = context.contentResolver

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val name = it.getString(nameIndex) ?: ""
                val phone = it.getString(phoneIndex)?.replace(" ", "") ?: ""
                contactList.add(ContactItem(name, phone))
            }
        }

        contactList
    }

}