package co.nimblehq.surveys.data.services.requests

import co.nimblehq.surveys.data.services.providers.ApiServiceProvider
import co.nimblehq.surveys.data.services.requests.login.LoginRequest
import co.nimblehq.surveys.data.services.requests.logout.LogoutRequest
import co.nimblehq.surveys.data.services.requests.refresh.token.RefreshTokenRequest
import co.nimblehq.surveys.data.storages.providers.NativeLibProvider
import javax.inject.Inject

class ClientRequestFactory @Inject constructor() {

    fun createLoginRequest(email: String, password: String) = LoginRequest(
        email = email,
        password = password,
        clientId = NativeLibProvider.clientId,
        clientSecret = NativeLibProvider.clientSecret,
        grantType = "password"
    )

    fun createLogoutRequest(token: String) = LogoutRequest(
        token = token,
        clientId = NativeLibProvider.clientId,
        clientSecret = NativeLibProvider.clientSecret,
    )

    fun createRefreshTokenRequest(refreshToken: String) = RefreshTokenRequest(
        refreshToken = refreshToken,
        clientId = NativeLibProvider.clientId,
        clientSecret = NativeLibProvider.clientSecret,
    )
}