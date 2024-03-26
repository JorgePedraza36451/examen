package com.project.examen.ui.model

import com.project.examen.base.Failure

sealed interface UiState {
    object Loading: UiState
    data class Error(val failure: Failure): UiState
    data class Success(val list: List<CharacterUiModel>): UiState
}