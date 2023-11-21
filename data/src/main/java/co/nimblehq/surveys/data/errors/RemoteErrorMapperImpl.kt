package co.nimblehq.surveys.data.errors

import co.nimblehq.surveys.data.services.providers.MoshiBuilderProvider
import co.nimblehq.surveys.data.services.responses.error.Error404Response
import co.nimblehq.surveys.data.services.responses.error.ErrorsResponse
import co.nimblehq.surveys.domain.errors.exceptions.network.NetworkCaughtException
import co.nimblehq.surveys.domain.errors.mappers.remote.RemoteErrorMapper
import co.nimblehq.surveys.domain.extensions.defaultEmpty
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.InterruptedIOException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteErrorMapperImpl @Inject constructor() : RemoteErrorMapper {

    override fun map(throwable: Throwable): NetworkCaughtException? {
        Timber.e(throwable)
        return when (throwable) {
            is UnknownHostException,
            is InterruptedIOException -> NetworkCaughtException.NoConnection

            is HttpException -> {
                if (throwable.code() == 404) {
                    val errorResponse = parseError404Response(throwable.response())
                    NetworkCaughtException.Server(
                        code = throwable.code(),
                        serverMsg = errorResponse?.error.defaultEmpty()
                    )
                } else {
                    val errorResponse = parseErrorsResponse(throwable.response())
                    val error = errorResponse?.errors?.firstOrNull()
                    NetworkCaughtException.Server(
                        code = throwable.code(),
                        serverMsg = error?.detail.defaultEmpty()
                    )
                }
            }

            else -> null
        }
    }

    private fun parseErrorsResponse(response: Response<*>?): ErrorsResponse? {
        val jsonString = response?.errorBody()?.string()
        return try {
            val moshi = MoshiBuilderProvider.moshiBuilder.build()
            val adapter = moshi.adapter(ErrorsResponse::class.java)
            adapter.fromJson(jsonString.defaultEmpty())
        } catch (exception: Exception) {
            Timber.e(exception)
            null
        }
    }

    private fun parseError404Response(response: Response<*>?): Error404Response? {
        val jsonString = response?.errorBody()?.string()
        return try {
            val moshi = MoshiBuilderProvider.moshiBuilder.build()
            val adapter = moshi.adapter(Error404Response::class.java)
            adapter.fromJson(jsonString.defaultEmpty())
        } catch (exception: Exception) {
            Timber.e(exception)
            null
        }
    }

}