package com.example.nit3213assessment2.network

import com.example.nit3213assessment2.data.LoginRequest
import com.example.nit3213assessment2.data.KeypassResponse
import com.example.nit3213assessment2.data.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiRetrieve {

    @POST("/footscray/auth")
    suspend fun getkeypass( @Body data: LoginRequest): LoginResponse

    @GET("/dashboard/{keypass}")
    suspend fun getAllEntities(@Path("keypass") keypass: String): KeypassResponse

    @GET("entities/{id}")
    suspend fun getObjectById(@Path("id") id: Int): KeypassResponse
}