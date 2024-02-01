package co.nimblehq.surveys.data.storages.providers

import android.content.Context
import androidx.room.Room
import co.nimblehq.surveys.data.storages.database.SurveyDatabases
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

object DatabaseProvider {
    fun getDatabase(context: Context): SurveyDatabases {
        val passPhrase: ByteArray = SQLiteDatabase.getBytes("nimble_survey".toCharArray())
        val factory = SupportFactory(passPhrase)
        return Room.databaseBuilder(context, SurveyDatabases::class.java, "app.db")
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}