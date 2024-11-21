package dev.propoc.flickrcodechallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.propoc.flickrcodechallenge.viewmodel.DetailViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule() // Ensures LiveData is updated instantly for testing.

    private val viewModel = DetailViewModel()

    @Test
    fun `convertDate should return correctly formatted date for valid input`() {
        val dateString = "2024-11-18T02:53:13Z" // ISO date-time format
        val expected = "18 Nov, 2024" // Expected format

        val result = viewModel.convertDate(dateString)

        assertEquals(expected, result)
    }

    @Test
    fun `convertDate should return 'Invalid date' for incorrect input`() {
        val dateString = "invalid-date-string"

        val result = viewModel.convertDate(dateString)

        assertEquals("Invalid date", result)
    }

    @Test
    fun `convertDate should handle empty input correctly`() {
        val dateString = ""

        val result = viewModel.convertDate(dateString)

        assertEquals("Invalid date", result)
    }
}
