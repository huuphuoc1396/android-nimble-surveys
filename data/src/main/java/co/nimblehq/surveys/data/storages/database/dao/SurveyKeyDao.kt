package co.nimblehq.surveys.data.storages.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.nimblehq.surveys.data.storages.database.entity.SurveyKeyEntity

@Dao
interface SurveyKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(surveyKeys: List<SurveyKeyEntity>)

    @Query("SELECT * FROM survey_keys WHERE surveyId = :surveyId")
    fun getSurveyKey(surveyId: String): SurveyKeyEntity

    @Query("DELETE FROM survey_keys")
    suspend fun deleteSurveyKeys()

    @Query("Select createdAt From survey_keys Order By createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}