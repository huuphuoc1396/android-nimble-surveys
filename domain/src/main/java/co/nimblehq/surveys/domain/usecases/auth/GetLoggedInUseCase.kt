package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.di.annotations.IoDispatcher
import co.nimblehq.surveys.domain.repositories.AuthRepository
import co.nimblehq.surveys.domain.usecases.StreamUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoggedInUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher,
    ) : StreamUseCase<Unit, Boolean>(dispatcher) {
        override suspend fun execute(params: Unit): Flow<Boolean> {
            return authRepository.isLoggedIn()
        }
    }
