package com.agamy.closeyourmouth.domain.usecase

import com.agamy.closeyourmouth.data.model.ContactItem
import com.agamy.closeyourmouth.data.model.User
import com.agamy.closeyourmouth.data.repository.NewChatRepository
import javax.inject.Inject

class GetAppContactsUseCase @Inject constructor(
    private val newChatRepository: NewChatRepository
) {

    // Fetches app users whose phone numbers match the provided contacts
    suspend operator fun invoke(contacts: List<ContactItem>): List<User> {
        // Delegate the fetching to the NewChatRepository
        return newChatRepository.getAppUsersMatchingContacts(contacts)
    }

}