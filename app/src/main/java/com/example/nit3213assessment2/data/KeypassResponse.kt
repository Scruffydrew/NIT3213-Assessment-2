package com.example.nit3213assessment2.data

// Response from GET request sent to api
data class KeypassResponse(
    val entities: List<Entity>,
    val entityTotal: Int
)