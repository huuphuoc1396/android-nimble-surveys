package co.nimblehq.surveys.data.storages.datastores

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

class EncryptedPrefsDatastoreImplTest {

    private val testScope = TestScope()
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val encryptedPrefsDatastoreImpl = EncryptedPrefsDatastoreImpl(
        coroutineScope = testScope,
        context = context,
    )

    @Test
    fun setLoggedIn_isLoggedIn_is_True() = testScope.runTest {
        encryptedPrefsDatastoreImpl.setLoggedIn(true)
        encryptedPrefsDatastoreImpl.isLoggedIn.test {
            expectMostRecentItem() shouldBe true
        }
    }

    @Test
    fun setLoggedIn_isLoggedIn_is_False() = testScope.runTest {
        encryptedPrefsDatastoreImpl.setLoggedIn(false)
        encryptedPrefsDatastoreImpl.isLoggedIn.test {
            expectMostRecentItem() shouldBe false
        }
    }

    @Test
    fun setTokenType() = testScope.runTest {
        encryptedPrefsDatastoreImpl.setTokenType("Bearer")
        encryptedPrefsDatastoreImpl.tokenType.test {
            expectMostRecentItem() shouldBe "Bearer"
        }
    }

    @Test
    fun setAccessToken() = testScope.runTest {
        encryptedPrefsDatastoreImpl.setAccessToken("vs7E5Akgf9zFRXCByT0zp1AIUFpFyxjkVZJoUgvgJMVleV3ew6qf16RRXYoUC51b")
        encryptedPrefsDatastoreImpl.accessToken.test {
            expectMostRecentItem() shouldBe "vs7E5Akgf9zFRXCByT0zp1AIUFpFyxjkVZJoUgvgJMVleV3ew6qf16RRXYoUC51b"
        }
    }

    @Test
    fun setRefreshToken() = testScope.runTest {
        encryptedPrefsDatastoreImpl.setRefreshToken("vs7E5Akgf9zFRXCByT0zp1AIUFpFyxjkVZJoUgvgJMVleV3ew6qf16RRXYoUC51b")
        encryptedPrefsDatastoreImpl.refreshToken.test {
            expectMostRecentItem() shouldBe "vs7E5Akgf9zFRXCByT0zp1AIUFpFyxjkVZJoUgvgJMVleV3ew6qf16RRXYoUC51b"
        }
    }

    @Test
    fun clearAll() = testScope.runTest {
        encryptedPrefsDatastoreImpl.clearAll()
        encryptedPrefsDatastoreImpl.isLoggedIn.test {
            expectMostRecentItem() shouldBe false
        }
        encryptedPrefsDatastoreImpl.tokenType.test {
            expectMostRecentItem() shouldBe ""
        }
        encryptedPrefsDatastoreImpl.accessToken.test {
            expectMostRecentItem() shouldBe ""
        }
        encryptedPrefsDatastoreImpl.refreshToken.test {
            expectMostRecentItem() shouldBe ""
        }
    }
}