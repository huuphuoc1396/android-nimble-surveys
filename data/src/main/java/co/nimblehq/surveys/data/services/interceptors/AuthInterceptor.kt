package co.nimblehq.surveys.data.services.interceptors

import co.nimblehq.surveys.data.storages.datastores.EncryptedPrefsDatastore
import co.nimblehq.surveys.domain.extensions.defaultEmpty
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val encryptedPrefsDatastore: EncryptedPrefsDatastore,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", getAuthToken())
            .build()
        return chain.proceed(request)
    }

    private fun getAuthToken() = runBlocking {
        val tokenType = encryptedPrefsDatastore.tokenType.firstOrNull().defaultEmpty()
        val accessToken = encryptedPrefsDatastore.accessToken.firstOrNull().defaultEmpty()
        return@runBlocking "$tokenType $accessToken"
    }
}