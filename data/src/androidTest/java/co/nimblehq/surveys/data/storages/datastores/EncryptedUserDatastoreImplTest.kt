package co.nimblehq.surveys.data.storages.datastores

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import co.nimblehq.surveys.domain.models.user.UserModel
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
class EncryptedUserDatastoreImplTest {
    private val testScope = TestScope()
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val encryptedUserDatastoreImpl = EncryptedUserDatastoreImpl(
        coroutineScope = testScope,
        context = context,
    )

    @Test
    fun saveAndFetchUser() = testScope.runTest {
        val testUser = UserModel(
            id = "1",
            name = "Tester",
            email = "tester@mail.com",
            avatarUrl = "https://nimbel.hq/avatar/1",
        )
        encryptedUserDatastoreImpl.saveUser(testUser)
        encryptedUserDatastoreImpl.getUser().test {
            expectMostRecentItem() shouldBe testUser
        }
    }

    @Test
    fun clearAll() = testScope.runTest {
        encryptedUserDatastoreImpl.clearAll()
        encryptedUserDatastoreImpl.getUser().test {
            expectMostRecentItem() shouldBe UserModel(id = "", name = "", email = "", avatarUrl = "")
        }
    }
}