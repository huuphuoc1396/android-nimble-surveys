package co.nimblehq.surveys.data.di.modules

import co.nimblehq.surveys.data.storages.datastore.EncryptedPrefsDatastore
import co.nimblehq.surveys.data.storages.EncryptedPrefsDatastoreImpl
import co.nimblehq.surveys.data.storages.EncryptedUserDatastoreImpl
import co.nimblehq.surveys.data.storages.datastore.EncryptedUserDatastore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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