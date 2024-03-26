package com.project.examen.data.mappers

import com.project.examen.data.response.ResultResponse
import com.project.examen.ui.model.CharacterUiModel

fun ResultResponse.toMapUiModel(): CharacterUiModel {
    return CharacterUiModel(
        image = image,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin?.name,
        location = location?.name
    )
}