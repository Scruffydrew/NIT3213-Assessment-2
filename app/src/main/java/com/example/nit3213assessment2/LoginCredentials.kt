package com.example.nit3213assessment2

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
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
        return "Lachlan"  // Provide the correct username here
    }

    @Provides
    @Singleton
    @Named("Password")
    fun providePassword(): String {
        return "s8093929"  // Provide the correct password here
    }

}