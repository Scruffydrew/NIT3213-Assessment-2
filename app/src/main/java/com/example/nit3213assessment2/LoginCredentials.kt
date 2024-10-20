package com.example.nit3213assessment2

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginCredentials {
    @Provides
    @Singleton
    @Named("Username")
    fun provideUsername(): String {
        return "Lachlan"  // Correct username
    }

    @Provides
    @Singleton
    @Named("Password")
    fun providePassword(): String {
        return "s8093929"  // Correct password
    }

}