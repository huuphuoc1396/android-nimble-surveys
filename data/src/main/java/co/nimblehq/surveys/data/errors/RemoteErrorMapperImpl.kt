package co.nimblehq.surveys.data.errors

import co.nimblehq.surveys.data.responses.ErrorResponse
import co.nimblehq.surveys.data.services.providers.MoshiBuilderProvider
import co.nimblehq.surveys.domain.errors.exceptions.network.NetworkCaughtException
import co.nimblehq.surveys.domain.errors.mappers.remote.RemoteErrorMapper
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
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
                val errorResponse = parseErrorResponse(throwable.response())
                NetworkCaughtException.Server(
                    code = errorResponse?.code ?: -1,
                    serverMsg = errorResponse?.message.orEmpty()
                )
            }

            else -> null
        }
    }

    private fun parseErrorResponse(response: Response<*>?): ErrorResponse? {
        val jsonString = response?.errorBody()?.string()
        return try {
            val moshi = MoshiBuilderProvider.moshiBuilder.build()
            val adapter = moshi.adapter(ErrorResponse::class.java)
            adapter.fromJson(jsonString.orEmpty())
        } catch (exception: IOException) {
            Timber.e(exception)
            null
        } catch (exception: JsonDataException) {
            Timber.e(exception)
            null
        }
    }

}