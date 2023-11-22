package co.nimblehq.surveys.data.errors

import co.nimblehq.surveys.domain.errors.exceptions.network.NetworkCaughtException
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.InterruptedIOException
import java.net.UnknownHostException

class RemoteErrorMapperImplTest {

    private val remoteErrorMapperImpl = RemoteErrorMapperImpl()

    private fun getMockHttpException(
        code: Int,
        body: String,
    ): HttpException {
        val response = mockk<Response<Any>>()
        val httpException = mockk<HttpException>()
        val responseBody = mockk<ResponseBody>()
        every { response.code() } returns code
        every { response.errorBody() } returns responseBody
        every { httpException.code() } returns response.code()
        every { httpException.response() } returns response
        every { responseBody.string() } returns body
        return httpException
    }

    @Test
    fun `When throwable is UnknownHostException`() {
        remoteErrorMapperImpl.map(UnknownHostException()) shouldBe NetworkCaughtException.NoConnection
    }

    @Test
    fun `When throwable is InterruptedIOException`() {
        remoteErrorMapperImpl.map(InterruptedIOException()) shouldBe NetworkCaughtException.NoConnection
    }

    @Test
    fun `When throwable is else`() {
        remoteErrorMapperImpl.map(Exception()) shouldBe null
    }

    @Test
    fun `When throwable is HttpException and code is 404`() {
        val mockHttpException = getMockHttpException(
            code = 404,
            body = """{
                "status": 404,
                "error": "Not found"
            }""".trimIndent()
        )
        remoteErrorMapperImpl.map(mockHttpException) shouldBe NetworkCaughtException.Server(
            code = 404,
            serverMsg = "Not found",
        )
    }

    @Test
    fun `When throwable is HttpException and code is others`() {
        val mockHttpException = getMockHttpException(
            code = 403,
            body = """{
                "errors": [
                    {
                        "source": "Doorkeeper::OAuth::Error",
                        "detail": "Client authentication failed due to unknown client",
                        "code": "invalid_client"
                    }
                ]
            }""".trimIndent()
        )
        remoteErrorMapperImpl.map(mockHttpException) shouldBe NetworkCaughtException.Server(
            code = 403,
            serverMsg = "Client authentication failed due to unknown client",
        )
    }
}