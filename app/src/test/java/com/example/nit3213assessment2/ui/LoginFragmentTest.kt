package com.example.nit3213assessment2.ui

import org.junit.Assert.*

import org.junit.Test

class LoginFragmentTest {

    lateinit var loginfragment: LoginFragment

    @Test
    fun Verify_Invalid_Password() {
        loginfragment=LoginFragment()
        var result = loginfragment.checkCredentials(
            "",
            "s8093929",
            "Lachlan",
            "s8093929"
        )
        assertFalse(result)
    }

    @Test
    fun Verify_Invalid_Username() {
        loginfragment=LoginFragment()
        var result = loginfragment.checkCredentials(
            "Lachlan",
            "",
            "Lachlan",
            "s8093929"
        )
        assertFalse(result)
    }

    @Test
    fun Verify_Valid_Credentials() {
        loginfragment=LoginFragment()
        var result = loginfragment.checkCredentials(
            "Lachlan",
            "s8093929",
            "Lachlan",
            "s8093929"
        )
        assertTrue(result)
    }


}