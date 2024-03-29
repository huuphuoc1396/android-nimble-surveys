package co.nimblehq.surveys.data.di.modules

import android.content.Context
import co.nimblehq.surveys.data.di.annotations.AuthClient
import co.nimblehq.surveys.data.di.annotations.NonAuthClient
import co.nimblehq.surveys.data.services.interceptors.AuthInterceptor
import co.nimblehq.surveys.data.services.interceptors.HeaderInterceptor
import co.nimblehq.surveys.data.services.interceptors.TokenAuthenticator
import co.nimblehq.surveys.data.services.providers.OkHttpClientProvider
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
class OkHttpClientModule {

    @Provides
    @NonAuthClient
    fun provideNonAuthOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        headerInterceptor: HeaderInterceptor,
    ) = OkHttpClientProvider.getOkHttpClientBuilder(
        httpLoggingInterceptor = httpLoggingInterceptor,
        chuckerInterceptor = chuckerInterceptor,
        headerInterceptor = headerInterceptor,
    ).build()

    @Provides
    @AuthClient
    fun provideAuthOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        headerInterceptor: HeaderInterceptor,
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ) = OkHttpClientProvider.getOkHttpClientBuilder(
        httpLoggingInterceptor = httpLoggingInterceptor,
        chuckerInterceptor = chuckerInterceptor,
        headerInterceptor = headerInterceptor,
        authInterceptor = authInterceptor,
        tokenAuthenticator = tokenAuthenticator,
    ).build()

    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context
    ): ChuckerInterceptor = OkHttpClientProvider.getChuckerInterceptor(context)

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        OkHttpClientProvider.getHttpLoggingInterceptor()

    @Provides
    fun provideHeaderInterceptor(): HeaderInterceptor =
        OkHttpClientProvider.getHeaderInterceptor()

}