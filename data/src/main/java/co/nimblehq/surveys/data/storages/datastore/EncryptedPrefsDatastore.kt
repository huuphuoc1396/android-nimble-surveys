package co.nimblehq.surveys.data.storages.datastore

import kotlinx.coroutines.flow.Flow

interface EncryptedPrefsDatastore {

    val isLoggedIn: Flow<Boolean>

    suspend fun setLoggedIn(isLoggedIn: Boolean)

    val tokenType: Flow<String>

    suspend fun setTokenType(tokenType: String)


    val accessToken: Flow<String>

    suspend fun setAccessToken(accessToken: String)

    val refreshToken: Flow<String>

    suspend fun setRefreshToken(refreshToken: String)

    suspend fun clearAll()
}