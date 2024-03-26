package com.project.examen.data.repository

import com.project.examen.data.mappers.toMapUiModel
import com.project.examen.data.service.CharacterService
import com.project.examen.ui.model.CharacterUiModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterRepository @Inject constructor (private val service: CharacterService) {
    fun getService(page: Int): Flow<List<CharacterUiModel>> = flow {
        service.getCharacterService(page).also {
            val dataParse = mutableListOf<CharacterUiModel>()
            it.results?.forEach { character ->
                dataParse.add(
                    character.toMapUiModel()
                )
            }
            emit(dataParse.toList())
        }
    }
}