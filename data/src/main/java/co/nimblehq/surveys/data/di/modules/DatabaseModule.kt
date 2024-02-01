package co.nimblehq.surveys.data.di.modules

import android.content.Context
import co.nimblehq.surveys.data.storages.database.SurveyDatabases
import co.nimblehq.surveys.data.storages.providers.DatabaseProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        DatabaseProvider.createSurveyDatabase(context)

    @Provides
    @Singleton
    fun provideSurveyDao(db: SurveyDatabases) = db.surveyDao()

    @Provides
    @Singleton
    fun provideSurveyKeyDao(db: SurveyDatabases) = db.surveyKeyDao()
}