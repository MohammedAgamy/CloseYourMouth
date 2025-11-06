package com.agamy.closeyourmouth.data.repository

import com.agamy.closeyourmouth.data.model.Message
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private fun getMessagesRef(chatId: String) =
        firestore.collection("chats").document(chatId).collection("messages")


    // إرسال رسالة
    suspend fun sendMessage(chatId: String, message: Message) {
        getMessagesRef(chatId).add(message).await()

        // تحديث آخر رسالة في الـ chat document
        firestore.collection("chats").document(chatId)
            .update(
                mapOf(
                    "lastMessage" to message.message,
                    "lastMessageTime" to message.timestamp
                )
            )
    }


    fun observeMessages(chatId: String): Flow<List<Message>> = callbackFlow {
        val listener = getMessagesRef(chatId)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val messages = snapshot?.toObjects(Message::class.java) ?: emptyList()
                trySend(messages)
            }

        awaitClose { listener.remove() }
    }
}