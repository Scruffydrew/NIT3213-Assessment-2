package com.example.nit3213assessment2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213assessment2.data.ApiRepository
import com.example.nit3213assessment2.data.Entity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository : ApiRepository) : ViewModel()  {

    private val _entitiesState = MutableStateFlow<List<Entity>>(emptyList())
    val entitiesState: StateFlow<List<Entity>> = _entitiesState

    // StateFlow to handle the entity total
    private val _entityTotalState = MutableStateFlow(0)
    val entityTotalState: StateFlow<Int> = _entityTotalState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun getAllObjects(keypass:String) {
        viewModelScope.launch {
            try {
                val response = repository.getAllEntities(keypass)
                _entitiesState.value = response.entities
                _entityTotalState.value = response.entityTotal
            } catch (e: Exception) {
                _errorState.value = "Error fetching objects: ${e.message}"
            }
        }
    }
}