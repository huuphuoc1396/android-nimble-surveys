package co.nimblehq.surveys.data.services.requests

import co.nimblehq.surveys.data.services.providers.ApiServiceProvider
import co.nimblehq.surveys.data.services.requests.login.LoginRequest
import co.nimblehq.surveys.data.services.requests.logout.LogoutRequest
import co.nimblehq.surveys.data.services.requests.refresh.token.RefreshTokenRequest
import javax.inject.Inject

class ClientRequestFactory
    @Inject
    constructor() {
        fun createLoginRequest(
            email: String,
            password: String,
        ) = LoginRequest(
            email = email,
            password = password,
            clientId = ApiServiceProvider.clientId,
            clientSecret = ApiServiceProvider.clientSecret,
            grantType = "password",
        )

        fun createLogoutRequest(token: String) =
            LogoutRequest(
                token = token,
                clientId = ApiServiceProvider.clientId,
                clientSecret = ApiServiceProvider.clientSecret,
            )

        fun createRefreshTokenRequest(refreshToken: String) =
            RefreshTokenRequest(
                refreshToken = refreshToken,
                clientId = ApiServiceProvider.clientId,
                clientSecret = ApiServiceProvider.clientSecret,
            )
    }
