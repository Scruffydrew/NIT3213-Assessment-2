package com.example.nit3213assessment2.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nit3213assessment2.KeypassRepository
import com.example.nit3213assessment2.data.ApiRepository
import com.example.nit3213assessment2.data.Entity
import com.example.nit3213assessment2.data.KeypassResponse
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DashboardViewModel
    private lateinit var repository: ApiRepository
    private lateinit var keypassRepository: KeypassRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Mock the repository
        repository = mockk()
        keypassRepository = mockk()

        // Mock the keypass return value from KeypassRepository
        every { keypassRepository.keypass } returns "fitness"

        // Set the dispatcher for the ViewModel's coroutine scope
        Dispatchers.setMain(testDispatcher)

        // Initialize the ViewModel with the mocked repository
        viewModel = DashboardViewModel(repository)
    }

    @Test
    fun getAllObjects_update_entitiesState_and_entityTotalState_on_successful_fetch() = runTest(testDispatcher) {
        // Arrange
        val mockEntities = listOf(
            Entity("Push Up", "Chest", "None", "Easy", 300, "A basic upper body exercise."),
            Entity("Squat", "Legs", "None", "Medium", 400, "A basic lower body exercise.")
        )
        coEvery { repository.getAllEntities(keypassRepository.keypass) } returns KeypassResponse(
            entities = mockEntities,
            entityTotal = mockEntities.size
        )

        // Act: Call the method using the mocked keypass value
        viewModel.getAllObjects(keypassRepository.keypass)

        // Advance time to allow the ViewModel's coroutine to execute
        advanceUntilIdle()

        // Assert that entitiesState is updated
        val entitiesState = viewModel.entitiesState.value
        assertEquals(2, entitiesState.size)
        assertEquals("Push Up", entitiesState[0].exerciseName)
        assertEquals("Squat", entitiesState[1].exerciseName)

        // Assert that entityTotalState is updated
        assertEquals(2, viewModel.entityTotalState.value)
    }

    @Test
    fun getAllObjects_errorState_on_fetch_failure() = runTest(testDispatcher) {
        // Arrange
        val errorMessage = "Network error"
        coEvery { repository.getAllEntities(keypassRepository.keypass) } throws RuntimeException(errorMessage)

        // Act: Call the method using the mocked keypass value
        viewModel.getAllObjects(keypassRepository.keypass)

        // Advance time to allow the ViewModel's coroutine to execute
        advanceUntilIdle()

        // Assert that errorState is updated
        assertEquals("Error fetching objects: $errorMessage", viewModel.errorState.value)
    }
}
