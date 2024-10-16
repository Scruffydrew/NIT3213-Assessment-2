package com.example.nit3213assessment2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213assessment2.data.keypassRequest
import com.example.nit3213assessment2.data.loginResponse
import com.example.nit3213assessment2.data.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel()  {
    private val repository = ApiRepository()

    private val mutableObjectsState = MutableStateFlow(loginResponse(keypass = ""))
    val objectsState: StateFlow<loginResponse> = mutableObjectsState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun getkeypass() {
        viewModelScope.launch {
            try {
                val entities = repository.getkeypass(keypassRequest("s8093929", "Lachlan"))
                mutableObjectsState.value = entities
            } catch (e: Exception) {
                _errorState.value = "Error fetching objects: ${e.message}"
            }
        }
    }
}