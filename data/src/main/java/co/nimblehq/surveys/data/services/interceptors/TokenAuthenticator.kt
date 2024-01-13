package co.nimblehq.surveys.data.services.interceptors

import co.nimblehq.surveys.data.services.NonAuthApiService
import co.nimblehq.surveys.data.services.requests.ClientRequestFactory
import co.nimblehq.surveys.data.storages.datastores.EncryptedPrefsDatastore
import co.nimblehq.surveys.domain.extensions.defaultEmpty
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val clientRequestFactory: ClientRequestFactory,
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
            val request = clientRequestFactory.createRefreshTokenRequest(refreshToken)
            nonAuthApiService.refreshToken(request).data
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
        if (data != null) {
            val tokenType = data.attributes?.tokenType.defaultEmpty()
            val accessToken = data.attributes?.accessToken.defaultEmpty()
            encryptedPrefsDatastore.setTokenType(tokenType)
            encryptedPrefsDatastore.setAccessToken(accessToken)
            encryptedPrefsDatastore.setRefreshToken(data.attributes?.refreshToken.defaultEmpty())
            encryptedPrefsDatastore.setLoggedIn(true)
            return@runBlocking "$tokenType $accessToken"
        }
        encryptedPrefsDatastore.setLoggedIn(false)
        return@runBlocking ""
    }
}