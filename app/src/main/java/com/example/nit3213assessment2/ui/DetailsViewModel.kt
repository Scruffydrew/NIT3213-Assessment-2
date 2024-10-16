package com.example.nit3213assessment2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213assessment2.data.keypassResponse
import com.example.nit3213assessment2.data.ApiRepository
import com.example.nit3213assessment2.data.Entity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel()  {
    private val repository = ApiRepository()

    private val _entitiesState = MutableStateFlow<List<Entity>>(emptyList())
    val entitiesState: StateFlow<List<Entity>> = _entitiesState

    // StateFlow to handle the entity total
    private val _entityTotalState = MutableStateFlow(0)
    val entityTotalState: StateFlow<Int> = _entityTotalState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun getAllObjects() {
        viewModelScope.launch {
            try {
                val response = repository.getAllEntities()
                _entitiesState.value = response.entities
                _entityTotalState.value = response.entityTotal
            } catch (e: Exception) {
                _errorState.value = "Error fetching objects: ${e.message}"
            }
        }
    }
}