package com.example.nit3213assessment2.ui

import org.junit.Assert.*

import org.junit.Test

class LoginFragmentTest {

    lateinit var loginfragment: LoginFragment

    // checkCredentials Tests
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

    // loginErrorReason Tests
    @Test
    fun LoginErrorMessage_Wrong_Username() {
        loginfragment=LoginFragment()
        var result = loginfragment.loginErrorReason(
            "",
            "s8093929",
            "Lachlan",
            "s8093929"
        )
        assertEquals(result, "Username is incorrect")
    }

    @Test
    fun LoginErrorMessage_Wrong_Password() {
        loginfragment=LoginFragment()
        var result = loginfragment.loginErrorReason(
            "Lachlan",
            "",
            "Lachlan",
            "s8093929"
        )
        assertEquals(result, "Password is incorrect")
    }

    @Test
    fun LoginErrorMessage_Wrong_Username_and_Password() {
        loginfragment=LoginFragment()
        var result = loginfragment.loginErrorReason(
            "",
            "",
            "Lachlan",
            "s8093929"
        )
        assertEquals(result, "Both username and password are incorrect")
    }
}