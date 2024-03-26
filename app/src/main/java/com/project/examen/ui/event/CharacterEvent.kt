package com.project.examen.ui.event

sealed class CharacterEvent {
    data object GetCharacter: CharacterEvent()
}