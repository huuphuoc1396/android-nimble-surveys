package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.di.annotations.IoDispatcher
import co.nimblehq.surveys.domain.models.user.UserModel
import co.nimblehq.surveys.domain.repositories.UserRepository
import co.nimblehq.surveys.domain.usecases.StreamUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : StreamUseCase<Unit, UserModel>(dispatcher) {

    override suspend fun execute(params: Unit): Flow<UserModel> {
        return userRepository.getUser()
    }
}