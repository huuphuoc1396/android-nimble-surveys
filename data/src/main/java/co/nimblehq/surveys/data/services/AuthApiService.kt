package co.nimblehq.surveys.data.services

import co.nimblehq.surveys.data.services.responses.DataResponse
import co.nimblehq.surveys.data.services.responses.user.UserResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {

    @GET("/api/v1/me")
    suspend fun getUser(): DataResponse<UserResponse>

}
