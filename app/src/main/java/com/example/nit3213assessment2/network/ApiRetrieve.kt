package com.example.nit3213assessment2.network

import com.example.nit3213assessment2.data.Entity
import com.example.nit3213assessment2.data.keypassRequest
import com.example.nit3213assessment2.data.keypassResponse
import com.example.nit3213assessment2.data.loginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiRetrieve {

    @POST("/footscray/auth")
    suspend fun getkeypass( @Body data: keypassRequest): loginResponse

    @GET("/dashboard/fitness")
    suspend fun getAllEntities(): keypassResponse

    @GET("entities/{id}")
    suspend fun getObjectById(@Path("id") id: Int): keypassResponse
}