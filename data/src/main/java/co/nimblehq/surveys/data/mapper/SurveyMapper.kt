package co.nimblehq.surveys.data.mapper

import co.nimblehq.surveys.data.storages.database.entity.SurveyEntity
import co.nimblehq.surveys.data.storages.database.entity.SurveyKeyEntity
import co.nimblehq.surveys.domain.extensions.defaultEmpty
import co.nimblehq.surveys.domain.models.survey.SurveyModel

fun SurveyModel.toEntity() =
    SurveyEntity(
        id = 0,
        surveyId = this.id.defaultEmpty(),
        title = this.title.defaultEmpty(),
        description = this.description.defaultEmpty(),
        coverUrl = this.coverUrl.defaultEmpty(),
    )

fun List<SurveyModel>.toSurveyEntities() =
    this.map { surveyModel ->
        surveyModel.toEntity()
    }

fun SurveyEntity.toSurveyModel() =
    SurveyModel(
        id = this.surveyId.defaultEmpty(),
        title = this.title.defaultEmpty(),
        description = this.description.defaultEmpty(),
        coverUrl = this.coverUrl.defaultEmpty(),
    )

fun SurveyModel.toSurveyKeyEntity(nextPage: Int?) =
    SurveyKeyEntity(
        surveyId = this.id.defaultEmpty(),
        nextPage = nextPage,
    )

fun List<SurveyModel>.toSurveyKeyEntities(nextPage: Int?) =
    this.map { surveyModel ->
        surveyModel.toSurveyKeyEntity(nextPage)
    }
