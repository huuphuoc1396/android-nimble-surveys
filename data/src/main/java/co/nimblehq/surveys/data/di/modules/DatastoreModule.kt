package co.nimblehq.surveys.data.di.modules

import co.nimblehq.surveys.data.storages.datastores.EncryptedPrefsDatastore
import co.nimblehq.surveys.data.storages.datastores.EncryptedPrefsDatastoreImpl
import co.nimblehq.surveys.data.storages.datastores.EncryptedUserDatastore
import co.nimblehq.surveys.data.storages.datastores.EncryptedUserDatastoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatastoreModule {
    @Binds
    @Singleton
    fun provideEncryptedPrefsDatastore(impl: EncryptedPrefsDatastoreImpl): EncryptedPrefsDatastore

    @Binds
    @Singleton
    fun provideEncryptedUserDatastore(impl: EncryptedUserDatastoreImpl): EncryptedUserDatastore
}
