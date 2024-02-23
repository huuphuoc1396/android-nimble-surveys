package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.di.annotations.IoDispatcher
import co.nimblehq.surveys.domain.repositories.AuthRepository
import co.nimblehq.surveys.domain.repositories.UserRepository
import co.nimblehq.surveys.domain.usecases.SingleUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : SingleUseCase<LoginUseCase.Params, Boolean>(dispatcher) {

    override suspend fun execute(params: Params): Boolean {
        val isLoggedIn = authRepository.login(
            email = params.email,
            password = params.password,
        )
        if (isLoggedIn) {
            userRepository.fetchUser()
        }
        return isLoggedIn
    }

    data class Params(
        val email: String,
        val password: String,
    )
}