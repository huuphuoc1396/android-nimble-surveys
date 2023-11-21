package co.nimblehq.surveys.data.di.modules

import co.nimblehq.surveys.data.repositories.AuthRepositoryImpl
import co.nimblehq.surveys.data.repositories.SurveyRepositoryImpl
import co.nimblehq.surveys.domain.repositories.AuthRepository
import co.nimblehq.surveys.domain.repositories.SurveyRepository
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

    @Binds
    @Singleton
    fun provideSurveyRepository(impl: SurveyRepositoryImpl): SurveyRepository
}