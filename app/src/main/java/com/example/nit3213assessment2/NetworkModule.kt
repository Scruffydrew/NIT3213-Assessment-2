package com.example.nit3213assessment2

import com.example.nit3213assessment2.network.ApiRetrieve
import com.example.nit3213assessment2.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideApiRetrieve(): ApiRetrieve {
        return RetrofitClient().apiService
    }
}
