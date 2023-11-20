package co.nimblehq.surveys.data.di.modules

import co.nimblehq.surveys.data.storages.datastore.EncryptedPrefsDatastore
import co.nimblehq.surveys.data.storages.EncryptedPrefsDatastoreImpl
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
}