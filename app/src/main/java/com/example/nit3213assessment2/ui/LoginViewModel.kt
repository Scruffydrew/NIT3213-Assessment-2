package com.example.nit3213assessment2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213assessment2.KeypassRepository
import com.example.nit3213assessment2.data.LoginRequest
import com.example.nit3213assessment2.data.LoginResponse
import com.example.nit3213assessment2.data.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository : ApiRepository,
    private val keypassRepository: KeypassRepository
) : ViewModel()  {

    private val mutableObjectsState = MutableStateFlow(LoginResponse(keypass = ""))
    val objectsState: StateFlow<LoginResponse> = mutableObjectsState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun getkeypass() {
        viewModelScope.launch {
            try {
                val entities = repository.getkeypass(LoginRequest("s8093929", "Lachlan"))
                mutableObjectsState.value = entities
                // Store the keypass in KeypassRepository
                keypassRepository.keypass = entities.keypass
            } catch (e: Exception) {
                _errorState.value = "Error fetching objects: ${e.message}"
            }
        }
    }
}