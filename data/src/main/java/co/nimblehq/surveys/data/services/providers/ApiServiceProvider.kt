package co.nimblehq.surveys.data.services.providers

import co.nimblehq.surveys.data.services.ApiService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
