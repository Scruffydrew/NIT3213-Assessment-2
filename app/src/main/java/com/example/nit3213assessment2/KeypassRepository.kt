package com.example.nit3213assessment2

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeypassRepository @Inject constructor() {
    var keypass: String = ""
}