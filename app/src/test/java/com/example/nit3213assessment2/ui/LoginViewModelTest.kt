package com.example.nit3213assessment2.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nit3213assessment2.data.ApiRepository
import com.example.nit3213assessment2.data.LoginRequest
import com.example.nit3213assessment2.data.LoginResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: ApiRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Mock the repository
        repository = mockk()

        // Set the dispatcher for the ViewModel's coroutine scope
        Dispatchers.setMain(testDispatcher)

        // Initialize the ViewModel with the mocked repository
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun getkeypass_update_objectsState_on_successful_fetch() = runTest(testDispatcher) {
        // Arrange
        val mockResponse = LoginResponse(keypass = "test_keypass")
        coEvery { repository.getkeypass(LoginRequest("s8093929", "Lachlan")) } returns mockResponse

        // Act
        viewModel.getkeypass()

        // Advance time to allow the ViewModel's coroutine to execute
        advanceUntilIdle()

        // Assert that objectsState is updated
        assertEquals(mockResponse, viewModel.objectsState.value)
    }

    @Test
    fun getkeypass_errorState_on_fetch_failure() = runTest(testDispatcher) {
        // Arrange
        val errorMessage = "Network error"
        coEvery { repository.getkeypass(LoginRequest("s8093929", "Lachlan")) } throws RuntimeException(errorMessage)

        // Act
        viewModel.getkeypass()

        // Advance time to allow the ViewModel's coroutine to execute
        advanceUntilIdle()

        // Assert that errorState is updated
        assertNotNull(viewModel.errorState.value)
        assertEquals("Error fetching objects: $errorMessage", viewModel.errorState.value)
    }
}
