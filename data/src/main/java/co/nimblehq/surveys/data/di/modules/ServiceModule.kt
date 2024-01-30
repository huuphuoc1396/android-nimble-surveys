package co.nimblehq.surveys.data.di.modules

import co.nimblehq.surveys.data.BuildConfig
import co.nimblehq.surveys.data.di.annotations.AuthClient
import co.nimblehq.surveys.data.di.annotations.NonAuthClient
import co.nimblehq.surveys.data.services.AuthApiService
import co.nimblehq.surveys.data.services.NonAuthApiService
import co.nimblehq.surveys.data.services.providers.ApiServiceProvider
import co.nimblehq.surveys.data.services.providers.CallAdapterFactoryProvider
import co.nimblehq.surveys.data.services.providers.ConverterFactoryProvider
import co.nimblehq.surveys.data.services.providers.RetrofitProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    fun provideBaseApiUrl() = BuildConfig.BASE_API_URL

    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): Converter.Factory = ConverterFactoryProvider.getMoshiConverterFactory(moshi)

    @Provides
    fun provideMappingApiErrorCallAdapterFactory(): CallAdapter.Factory = CallAdapterFactoryProvider.getMappingApiErrorCallAdapterFactory()

    @Provides
    @NonAuthClient
    fun provideNonAuthRetrofit(
        baseUrl: String,
        @NonAuthClient okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
    ): Retrofit =
        RetrofitProvider
            .getRetrofitBuilder(
                baseUrl = baseUrl,
                okHttpClient = okHttpClient,
                converterFactory = converterFactory,
                callAdapterFactory = callAdapterFactory,
            )
            .build()

    @Provides
    fun provideNonAuthApiService(
        @NonAuthClient retrofit: Retrofit,
    ): NonAuthApiService = ApiServiceProvider.getNonAuthApiService(retrofit)

    @Provides
    @AuthClient
    fun provideAuthRetrofit(
        baseUrl: String,
        @AuthClient okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
    ): Retrofit =
        RetrofitProvider
            .getRetrofitBuilder(
                baseUrl = baseUrl,
                okHttpClient = okHttpClient,
                converterFactory = converterFactory,
                callAdapterFactory = callAdapterFactory,
            )
            .build()

    @Provides
    fun provideAuthApiService(
        @AuthClient retrofit: Retrofit,
    ): AuthApiService = ApiServiceProvider.getAuthApiService(retrofit)
}
