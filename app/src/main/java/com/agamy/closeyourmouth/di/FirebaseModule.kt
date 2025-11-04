package com.agamy.closeyourmouth.di


import com.agamy.closeyourmouth.data.remote.AuthRepository
import com.agamy.closeyourmouth.data.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
// FirebaseModule is a Dagger Hilt module that provides Firebase-related dependencies.
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    // This function tells Hilt how to create (or "provide") a FirebaseAuth instance.
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        // FirebaseAuth.getInstance() is the standard way to get the singleton Firebase Auth object.
        return FirebaseAuth.getInstance()
    }

    // This function tells Hilt how to create (or "provide") an AuthRepository instance.
    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(auth)
    }
}