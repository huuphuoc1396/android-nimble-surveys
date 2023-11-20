package co.nimblehq.surveys.data.services.providers

import co.nimblehq.surveys.data.services.NonAuthApiService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getNonAuthApiService(retrofit: Retrofit): NonAuthApiService {
        return retrofit.create(NonAuthApiService::class.java)
    }
}
