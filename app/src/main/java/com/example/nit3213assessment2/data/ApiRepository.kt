package com.example.nit3213assessment2.data

import com.example.nit3213assessment2.network.ApiRetrieve
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiRetrieve // Now ApiRetrieve is injected directly
) {

    suspend fun getkeypass(newObject: LoginRequest): LoginResponse {
        return apiService.getkeypass(newObject)
    }

    suspend fun getAllEntities(keypass: String): KeypassResponse {
        return apiService.getAllEntities(keypass)
    }

    suspend fun getObjectById(id: Int): KeypassResponse {
        return apiService.getObjectById(id)
    }
}
