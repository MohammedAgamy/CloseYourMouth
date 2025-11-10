package com.agamy.closeyourmouth.data.repository

import com.agamy.closeyourmouth.data.model.ContactItem
import com.agamy.closeyourmouth.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewChatRepository @Inject constructor(
    private val firestore : FirebaseFirestore
) {

    // Fetches users from Firestore whose phone numbers match the provided contacts
    suspend fun getAppUsersMatchingContacts(contacts: List<ContactItem>): List<User> {
        val snapshot = firestore.collection("saveusers").get().await()
        val allUsers = snapshot.toObjects(User::class.java)
        // Filter users whose phone numbers match any of the contacts' phone numbers
        return allUsers.filter { user ->
            // Check if any contact matches the user's phone number
            contacts.any { contact ->
                // Compare the last 8 digits of the phone numbers for matching
                contact.phone.takeLast(8).contains(user.phone.takeLast(8))
            }
        }
    }

}