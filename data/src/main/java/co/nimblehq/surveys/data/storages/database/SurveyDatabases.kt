package co.nimblehq.surveys.data.storages.database

import androidx.room.Database
import androidx.room.RoomDatabase
import co.nimblehq.surveys.data.storages.database.dao.SurveyDao
import co.nimblehq.surveys.data.storages.database.dao.SurveyKeyDao
import co.nimblehq.surveys.data.storages.database.entity.SurveyEntity

@Database(
    entities = [SurveyEntity::class, SurveyKeyDao::class],
    version = 1,
    exportSchema = false
)
abstract class SurveyDatabases : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao
    abstract fun surveyKeyDao(): SurveyKeyDao
}