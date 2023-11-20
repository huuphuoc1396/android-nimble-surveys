package co.nimblehq.surveys.data.services.interceptors

import co.nimblehq.surveys.data.services.NonAuthApiService
import co.nimblehq.surveys.data.services.requests.refresh.token.RefreshTokenRequest
import co.nimblehq.surveys.data.storages.datastore.EncryptedPrefsDatastore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val nonAuthApiService: NonAuthApiService,
    private val encryptedPrefsDatastore: EncryptedPrefsDatastore,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
        val newToken = refreshToken()
        return response.request.newBuilder()
            .header("Authorization", newToken)
            .build()
    }

    private fun refreshToken(): String = runBlocking {
        val refreshToken = encryptedPrefsDatastore
            .refreshToken
            .firstOrNull() ?: return@runBlocking ""
        val data = try {
            val refreshTokenRequest = RefreshTokenRequest(refreshToken = refreshToken)
            nonAuthApiService.refreshToken(refreshTokenRequest).data
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
        if (data != null) {
            val newToken = data.attributes?.authToken.orEmpty()
            encryptedPrefsDatastore.setAccessToken(data.attributes?.authToken.orEmpty())
            encryptedPrefsDatastore.setRefreshToken(data.attributes?.refreshToken.orEmpty())
            encryptedPrefsDatastore.setLoggedIn(true)
            return@runBlocking newToken
        }
        encryptedPrefsDatastore.setLoggedIn(false)
        return@runBlocking ""
    }
}