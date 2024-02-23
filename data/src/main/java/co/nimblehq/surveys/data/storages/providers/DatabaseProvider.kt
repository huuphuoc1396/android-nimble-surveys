package co.nimblehq.surveys.data.storages.providers

import android.content.Context
import androidx.room.Room
import co.nimblehq.surveys.data.BuildConfig
import co.nimblehq.surveys.data.storages.database.SurveyDatabases
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

object DatabaseProvider {
    private const val SURVEY_DB_NAME = "nimble_survey.db"

    fun createSurveyDatabase(context: Context): SurveyDatabases {
        return if (BuildConfig.DEBUG) {
            Room.databaseBuilder(context, SurveyDatabases::class.java, SURVEY_DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        } else {
            val passPhrase: ByteArray =
                SQLiteDatabase.getBytes(NativeLibProvider.sqliteEncryptedKey.toCharArray())
            val factory = SupportFactory(passPhrase)
            Room.databaseBuilder(context, SurveyDatabases::class.java, SURVEY_DB_NAME)
                .openHelperFactory(factory)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }
}