package co.nimblehq.surveys.data.storages.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "survey_keys")
data class SurveyKeyEntity(
    @PrimaryKey
    @ColumnInfo("surveyId")
    val surveyId: String,
    @ColumnInfo("nexPage")
    val nexPage: Int?,
)