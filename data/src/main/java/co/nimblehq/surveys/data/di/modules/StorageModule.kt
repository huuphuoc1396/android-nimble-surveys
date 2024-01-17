package co.nimblehq.surveys.data.di.modules

import co.nimblehq.surveys.data.storages.datastores.EncryptedPrefsDatastore
import co.nimblehq.surveys.data.storages.datastores.EncryptedPrefsDatastoreImpl
import co.nimblehq.surveys.data.storages.datastores.EncryptedUserDatastore
import co.nimblehq.surveys.data.storages.datastores.EncryptedUserDatastoreImpl
import co.nimblehq.surveys.domain.di.annotations.IoDispatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StorageModule {

    @Binds
    @Singleton
    fun provideEncryptedPrefsDatastore(impl: EncryptedPrefsDatastoreImpl): EncryptedPrefsDatastore

    @Binds
    @Singleton
    fun provideEncryptedUserDatastore(impl: EncryptedUserDatastoreImpl): EncryptedUserDatastore
}