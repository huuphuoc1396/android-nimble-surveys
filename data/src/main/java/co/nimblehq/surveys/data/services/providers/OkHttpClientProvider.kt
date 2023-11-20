package co.nimblehq.surveys.data.services.providers

import android.content.Context
import co.nimblehq.surveys.data.BuildConfig
import co.nimblehq.surveys.data.services.interceptors.HeaderInterceptor
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientProvider {

    private const val READ_TIMEOUT_IN_SECS = 30L
    private const val WRITE_TIMEOUT_IN_SECS = 30L

    fun getOkHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        headerInterceptor: HeaderInterceptor,
    ): OkHttpClient.Builder = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(chuckerInterceptor)
        }
        addInterceptor(headerInterceptor)
        readTimeout(READ_TIMEOUT_IN_SECS, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT_IN_SECS, TimeUnit.SECONDS)
    }

    fun getHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun getChuckerInterceptor(context: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .alwaysReadResponseBody(true)
            .build()
    }

    fun getHeaderInterceptor() = HeaderInterceptor()
}