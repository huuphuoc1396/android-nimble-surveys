package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.di.IoDispatcher
import co.nimblehq.surveys.domain.errors.exceptions.CaughtException
import co.nimblehq.surveys.domain.errors.mappers.ErrorMapper
import co.nimblehq.surveys.domain.models.UserModel
import co.nimblehq.surveys.domain.repositories.AuthRepository
import co.nimblehq.surveys.domain.usecases.EmptyParams
import co.nimblehq.surveys.domain.usecases.StreamUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetUserUseCase constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : StreamUseCase<EmptyParams, UserModel>(dispatcher) {

    override suspend fun execute(
        params: EmptyParams,
        errorMapper: ErrorMapper<CaughtException>
    ): Flow<UserModel> {
        return authRepository.getUser()
    }
}