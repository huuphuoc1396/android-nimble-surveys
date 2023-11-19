package co.nimblehq.surveys.data.di.modules

import co.nimblehq.surveys.data.repositories.AuthRepositoryImpl
import co.nimblehq.surveys.domain.repositories.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}