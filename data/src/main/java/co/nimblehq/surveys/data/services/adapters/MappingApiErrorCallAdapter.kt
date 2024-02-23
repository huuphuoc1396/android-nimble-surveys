package co.nimblehq.surveys.data.services.adapters

import co.nimblehq.surveys.data.services.providers.MoshiBuilderProvider
import co.nimblehq.surveys.data.services.responses.error.Error404Response
import co.nimblehq.surveys.data.services.responses.error.ErrorsResponse
import co.nimblehq.surveys.domain.extensions.defaultEmpty
import co.nimblehq.surveys.domain.extensions.defaultZero
import co.nimblehq.surveys.domain.models.errors.ApiError
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import java.io.InterruptedIOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.UnknownHostException

class MappingApiErrorCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *> {
        val responseType = getParameterUpperBound(0, returnType as ParameterizedType)
        return MappingApiErrorCallAdapter<Any>(responseType)
    }

    companion object {
        fun create() = MappingApiErrorCallAdapterFactory()
    }
}

class MappingApiErrorCallAdapter<R>(
    private val responseType: Type,
) : CallAdapter<R, Call<R>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): Call<R> = MappingApiErrorCall(call)

    private class MappingApiErrorCall<R>(
        private val delegate: Call<R>,
    ) : Call<R> {
        override fun enqueue(callback: Callback<R>) {
            delegate.enqueue(MappingApiErrorCallback(callback))
        }

        override fun clone(): Call<R> = MappingApiErrorCall(delegate.clone())

        override fun execute(): Response<R> = delegate.execute()

        override fun isExecuted(): Boolean = delegate.isExecuted

        override fun cancel() = delegate.cancel()

        override fun isCanceled(): Boolean = delegate.isCanceled

        override fun request(): Request = delegate.request()

        override fun timeout(): Timeout = delegate.timeout()
    }
}

class MappingApiErrorCallback<R>(
    private val delegate: Callback<R>,
) : Callback<R> {
    override fun onResponse(
        call: Call<R>,
        response: Response<R>,
    ) {
        when {
            response.isSuccessful -> delegate.onResponse(call, response)
            response.code() == 404 -> delegate.onFailure(call, parseError404Response(response))
            else -> delegate.onFailure(call, parseErrorsResponse(response))
        }
    }

    override fun onFailure(
        call: Call<R>,
        t: Throwable,
    ) {
        when (t) {
            is UnknownHostException,
            is InterruptedIOException,
            -> delegate.onFailure(call, ApiError.NoConnection)

            else -> delegate.onFailure(call, t)
        }
    }

    private fun parseErrorsResponse(response: Response<*>?): ApiError.Server {
        val jsonString = response?.errorBody()?.string()
        val errorResponse =
            try {
                val moshi = MoshiBuilderProvider.moshiBuilder.build()
                val adapter = moshi.adapter(ErrorsResponse::class.java)
                adapter.fromJson(jsonString.defaultEmpty())
            } catch (exception: Exception) {
                Timber.e(exception)
                null
            }
        val error = errorResponse?.errors?.firstOrNull()
        return ApiError.Server(
            code = response?.code().defaultZero(),
            serverMsg = error?.detail.defaultEmpty(),
        )
    }

    private fun parseError404Response(response: Response<*>?): ApiError.Server {
        val jsonString = response?.errorBody()?.string()
        val errorResponse =
            try {
                val moshi = MoshiBuilderProvider.moshiBuilder.build()
                val adapter = moshi.adapter(Error404Response::class.java)
                adapter.fromJson(jsonString.defaultEmpty())
            } catch (exception: Exception) {
                Timber.e(exception)
                null
            }
        return ApiError.Server(
            code = response?.code().defaultZero(),
            serverMsg = errorResponse?.error.defaultEmpty(),
        )
    }
}
