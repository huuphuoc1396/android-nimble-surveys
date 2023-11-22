package co.nimblehq.surveys.data.storages

import android.content.Context
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import co.nimblehq.surveys.data.storages.datastore.EncryptedPrefsDatastore
import co.nimblehq.surveys.data.storages.datastore.clearAll
import co.nimblehq.surveys.data.storages.datastore.get
import co.nimblehq.surveys.data.storages.datastore.set
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.osipxd.security.crypto.createEncrypted
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedPrefsDatastoreImpl @Inject constructor(
    @ApplicationContext context: Context
) : EncryptedPrefsDatastore {

    companion object {
        private const val FILE_NAME = "encrypted_prefs.preferences_pb"

        private val KEY_LOGGED_IN = booleanPreferencesKey("logged_in")
        private val KEY_TOKEN_TYPE = stringPreferencesKey("token_type")
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    private val dataStore = PreferenceDataStoreFactory.createEncrypted {
        EncryptedFile.Builder(
            context.dataStoreFile(FILE_NAME),
            context,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()
    }

    override val isLoggedIn: Flow<Boolean> = dataStore.get(KEY_LOGGED_IN, default = false)

    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        dataStore.set(KEY_LOGGED_IN, isLoggedIn)
    }

    override val tokenType: Flow<String> = dataStore.get(KEY_TOKEN_TYPE, "")

    override suspend fun setTokenType(tokenType: String) {
        dataStore.set(KEY_TOKEN_TYPE, tokenType)
    }

    override val accessToken: Flow<String> = dataStore.get(KEY_ACCESS_TOKEN, "")

    override suspend fun setAccessToken(accessToken: String) {
        dataStore.set(KEY_ACCESS_TOKEN, accessToken)
    }

    override val refreshToken: Flow<String> = dataStore.get(KEY_REFRESH_TOKEN, "")

    override suspend fun setRefreshToken(refreshToken: String) {
        dataStore.set(KEY_REFRESH_TOKEN, refreshToken)
    }

    override suspend fun clearAll() {
        dataStore.clearAll()
    }
}