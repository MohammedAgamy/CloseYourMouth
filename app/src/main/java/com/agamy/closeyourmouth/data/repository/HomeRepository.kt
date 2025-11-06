package com.agamy.closeyourmouth.data.repository

import com.agamy.closeyourmouth.data.model.ChatItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {


    suspend fun getUserChat(currentUserId: String): List<ChatItem> {
        val snapshot = firestore.collection("chats")
            .whereArrayContains("users", currentUserId)
            .orderBy("lastMessageTime", Query.Direction.DESCENDING)
            .get().await()

        return snapshot.documents.mapNotNull { doc ->
            val users = doc.get("users") as? List<String> ?: return@mapNotNull null
            val otherUserId = users.firstOrNull { it != currentUserId } ?: return@mapNotNull null


            val lastMessage = doc.getString("lastMessage") ?: ""
            val lastMessageTime = doc.getLong("lastMessageTime") ?: 0L


            val userDoc = firestore.collection("users").document(otherUserId).get().await()
            val otherUserName = userDoc.getString("name") ?: "Unknown"
            val otherUserImage = userDoc.getString("profileImage") ?: ""

            ChatItem(
                chatId = doc.id,
                otherUserId = otherUserId,
                otherUserName = otherUserName,
                lastMessage = lastMessage,
                lastMessageTime = lastMessageTime,
                otherUserImage = otherUserImage
            )


        }

    }
}