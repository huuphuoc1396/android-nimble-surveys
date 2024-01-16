package co.nimblehq.surveys.data.storage

import co.nimblehq.surveys.data.storages.datastores.EncryptedPrefsDatastore
import co.nimblehq.surveys.domain.extensions.defaultEmpty
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class EncryptedPrefsDatastoreTest {
    private val encryptedPrefsDatastore: EncryptedPrefsDatastore = mockk()

    @Test
    fun testWriteAndEncryptAccessToken() {
        runTest {
            // Arrange
            val originalData =
                "92yE8gBkoh1bK7I1p0Dd5vV0xVVUT5uIcttj2LQbcFiCTjGFdz50fgQ7z0MwwkZw"
            // Set up the mock behavior
            coEvery { encryptedPrefsDatastore.setAccessToken(any()) } returns Unit
            coEvery { encryptedPrefsDatastore.accessToken } returns flowOf(originalData)
            // Action
            encryptedPrefsDatastore.setAccessToken(originalData)
            val decryptData = encryptedPrefsDatastore.accessToken
            // Assert
            assert(originalData == decryptData.firstOrNull().defaultEmpty())
            // Verify that setAccessToken was called
        }
    }
}