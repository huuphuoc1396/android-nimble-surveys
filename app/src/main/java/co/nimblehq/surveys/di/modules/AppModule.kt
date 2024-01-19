package co.nimblehq.surveys.di.modules

import co.nimblehq.surveys.domain.di.annotations.ApplicationScope
import co.nimblehq.surveys.domain.di.annotations.DatastoreScope
import co.nimblehq.surveys.domain.di.annotations.DefaultDispatcher
import co.nimblehq.surveys.domain.di.annotations.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @ApplicationScope
    @Singleton
    @Provides
    fun providesApplicationScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)


    @DatastoreScope
    @Singleton
    @Provides
    fun provideDatastoreScope(@IoDispatcher dispatcher: CoroutineDispatcher) =
        CoroutineScope(dispatcher + SupervisorJob())
}
