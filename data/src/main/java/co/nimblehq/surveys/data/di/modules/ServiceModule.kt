package co.nimblehq.surveys.data.di.modules

import co.nimblehq.surveys.data.services.ApiService
import co.nimblehq.surveys.data.services.providers.ApiServiceProvider
import co.nimblehq.surveys.data.services.providers.ConverterFactoryProvider
import co.nimblehq.surveys.data.services.providers.RetrofitProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideBaseApiUrl() = ""

    @Provides
    fun provideMoshiConverterFactory(
        moshi: Moshi
    ): Converter.Factory = ConverterFactoryProvider.getMoshiConverterFactory(moshi)

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit = RetrofitProvider
        .getRetrofitBuilder(baseUrl, okHttpClient, converterFactory)
        .build()

    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService = ApiServiceProvider.getApiService(retrofit)
}