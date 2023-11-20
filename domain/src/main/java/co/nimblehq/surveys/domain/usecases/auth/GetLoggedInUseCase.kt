package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.di.annotations.IoDispatcher
import co.nimblehq.surveys.domain.errors.exceptions.CaughtException
import co.nimblehq.surveys.domain.errors.mappers.ErrorMapper
import co.nimblehq.surveys.domain.errors.mappers.remote.RemoteErrorMapper
import co.nimblehq.surveys.domain.repositories.AuthRepository
import co.nimblehq.surveys.domain.usecases.EmptyParams
import co.nimblehq.surveys.domain.usecases.StreamUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
    remoteErrorMapper: RemoteErrorMapper,
) : StreamUseCase<EmptyParams, Boolean>(dispatcher, remoteErrorMapper) {

    override suspend fun execute(
        params: EmptyParams,
        errorMapper: ErrorMapper<CaughtException>
    ): Flow<Boolean> {
        return authRepository.isLoggedIn()
    }
}