package co.nimblehq.surveys.domain.usecases

interface UseCase<in P, out R> {
    suspend fun execute(params: P): R
}

object EmptyParams