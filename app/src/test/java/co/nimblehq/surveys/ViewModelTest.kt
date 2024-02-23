package co.nimblehq.surveys

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

@ExperimentalCoroutinesApi
abstract class ViewModelTest {

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    open fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    open fun teardown() {
        Dispatchers.resetMain()
    }

}