import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.propoc.flickrcodechallenge.viewmodel.SearchViewModel
import dev.propoc.flickrcodechallenge.repository.Repository
import dev.propoc.flickrcodechallenge.model.ImageResponseModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // This is needed for LiveData and StateFlow

    private lateinit var viewModel: SearchViewModel
    private val mockRepository = mockk<Repository>()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Set the test dispatcher to control coroutines
        Dispatchers.setMain(testDispatcher)

        // Create the ViewModel and inject the mock repository
        viewModel = SearchViewModel(mockRepository)
    }

    @Test
    fun `test fetchData success`() = runTest {
        // Arrange: Mock repository to return a dummy data
        val dummyData = ImageResponseModel(id = "1", title = "Test Image")
        coEvery { mockRepository.getData(any()) } returns dummyData

        // Act: Call fetchData on the ViewModel
        viewModel.fetchData("test")

        // Advance coroutine execution
        testDispatcher.advanceUntilIdle()

        // Assert: Verify the result of the data fetching
        assertTrue(viewModel.dataList.value.isNotEmpty())  // Ensure data is loaded
        assertEquals(viewModel.dataList.value[0].id, "1")  // Verify the ID of the first image
        assertEquals(viewModel.dataList.value[0].title, "Test Image")  // Verify the title
        assertEquals(viewModel.isLoading.value, false)  // Ensure loading is finished
        assertEquals(viewModel.errorMessage.value, null)  // Ensure no error occurred
    }

    @Test
    fun `test fetchData failure`() = runTest {
        // Arrange: Mock repository to throw an exception
        coEvery { mockRepository.getData(any()) } throws Exception("Network error")

        // Act: Call fetchData on the ViewModel
        viewModel.fetchData("test")

        // Advance coroutine execution
        testDispatcher.advanceUntilIdle()

        // Assert: Verify the result in case of failure
        assertTrue(viewModel.dataList.value.isEmpty())  // Ensure no data is loaded
        assertEquals(viewModel.isLoading.value, false)  // Ensure loading is finished
        assertEquals(viewModel.errorMessage.value, "Failed to load data: Network error")  // Verify error message
    }

    @After
    fun tearDown() {
        // Reset the dispatcher
        Dispatchers.resetMain()
    }
}
