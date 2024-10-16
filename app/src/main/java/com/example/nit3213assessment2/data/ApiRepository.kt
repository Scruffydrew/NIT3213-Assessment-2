package com.example.nit3213assessment2.data

import com.example.nit3213assessment2.data.keypassRequest
import com.example.nit3213assessment2.data.keypassResponse
import com.example.nit3213assessment2.data.loginResponse
import com.example.nit3213assessment2.network.ApiRetrieve
import com.example.nit3213assessment2.network.RetrofitClient

class ApiRepository(private val apiService: ApiRetrieve = RetrofitClient().apiService) {

    suspend fun getkeypass(newObject: keypassRequest): loginResponse {
        return apiService.getkeypass(newObject)
    }

    suspend fun getAllEntities(): keypassResponse {
        return apiService.getAllEntities()
    }

    suspend fun getObjectById(id: Int): keypassResponse {
        return apiService.getObjectById(id)
    }
}
