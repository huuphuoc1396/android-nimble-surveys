package co.nimblehq.surveys.data.services

import co.nimblehq.surveys.data.services.requests.login.LoginRequest
import co.nimblehq.surveys.data.services.requests.logout.LogoutRequest
import co.nimblehq.surveys.data.services.requests.refresh.token.RefreshTokenRequest
import co.nimblehq.surveys.data.services.responses.DataResponse
import co.nimblehq.surveys.data.services.responses.login.LoginResponse
import co.nimblehq.surveys.data.services.responses.refresh.token.RefreshTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface NonAuthApiService {

    @POST("/api/v1/oauth/token")
    suspend fun login(@Body request: LoginRequest): DataResponse<LoginResponse>

    @POST("/api/v1/oauth/token")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): DataResponse<RefreshTokenResponse>

    @POST("/api/v1/oauth/revoke")
    suspend fun logout(@Body request: LogoutRequest)
}
