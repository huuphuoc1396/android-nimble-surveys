package co.nimblehq.surveys.data.di.modules

import co.nimblehq.surveys.data.errors.RemoteErrorMapperImpl
import co.nimblehq.surveys.domain.errors.mappers.remote.RemoteErrorMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ErrorMapperModule {

    @Binds
    fun provideRemoteErrorMapper(impl: RemoteErrorMapperImpl): RemoteErrorMapper
}