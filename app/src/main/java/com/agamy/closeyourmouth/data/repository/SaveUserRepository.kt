package com.agamy.closeyourmouth.data.repository

import com.agamy.closeyourmouth.data.model.SaveUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveUserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun saveUserToFirestore(user: SaveUser) {
        firestore.collection("saveusers")
            .document(user.userId)
            .set(user)
            .await()
    }
}