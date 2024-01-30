package co.nimblehq.surveys.data.services.providers

import co.nimblehq.surveys.data.services.AuthApiService
import co.nimblehq.surveys.data.services.NonAuthApiService
import retrofit2.Retrofit

object ApiServiceProvider {
    init {
        System.loadLibrary("native-lib")
    }

    @JvmStatic
    external fun getApiKey(id: Int): String

    val clientId: String
        get() = getApiKey(1)

    val clientSecret: String
        get() = getApiKey(2)

    fun getNonAuthApiService(retrofit: Retrofit): NonAuthApiService {
        return retrofit.create(NonAuthApiService::class.java)
    }

    fun getAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }
}
