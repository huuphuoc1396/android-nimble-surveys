package co.nimblehq.surveys.data.di.modules

import android.content.Context
import androidx.room.Room
import co.nimblehq.surveys.data.storages.database.SurveyDatabases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): SurveyDatabases {
        val passPhrase: ByteArray = SQLiteDatabase.getBytes("nimble_survey".toCharArray())
        val factory = SupportFactory(passPhrase)
        return Room.databaseBuilder(context, SurveyDatabases::class.java, "app.db")
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideSurveyDao(db: SurveyDatabases) = db.surveyDao()

    @Provides
    @Singleton
    fun provideSurveyKeyDao(db: SurveyDatabases) = db.surveyKeyDao()
}