package co.nimblehq.surveys.data.services

import co.nimblehq.surveys.data.responses.UserResponse
import retrofit2.http.GET

interface ApiService {

    @GET("/api/users")
    suspend fun getUsers(): UserResponse
}
