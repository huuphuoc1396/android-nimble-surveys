package co.nimblehq.surveys.data.storages.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.nimblehq.surveys.data.storages.database.entity.SurveyEntity

@Dao
interface SurveyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(surveys: List<SurveyEntity>)

    @Query("SELECT * FROM surveys ORDER BY id ASC")
    fun getSurveys(): PagingSource<Int, SurveyEntity>

    @Query("DELETE FROM surveys")
    suspend fun deleteSurveys()
}