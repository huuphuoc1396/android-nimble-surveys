package co.nimblehq.surveys.data.services

import co.nimblehq.surveys.data.services.responses.DataResponse
import co.nimblehq.surveys.data.services.responses.survey.SurveyListResponse
import co.nimblehq.surveys.data.services.responses.user.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApiService {
    @GET("/api/v1/me")
    suspend fun getUser(): DataResponse<UserResponse>

    @GET("/api/v1/surveys")
    suspend fun getSurveyList(
        @Query("page[number]") page: Int,
        @Query("page[size]") size: Int,
    ): SurveyListResponse
}
