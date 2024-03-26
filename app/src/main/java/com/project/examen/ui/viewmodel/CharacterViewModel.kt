package com.project.examen.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.examen.base.Failure
import com.project.examen.domain.usescases.CharacterUsesCases
import com.project.examen.ui.event.CharacterEvent
import com.project.examen.ui.model.CharacterUiModel
import com.project.examen.ui.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val usesCases: CharacterUsesCases
): ViewModel() {

    private val _uiEvent = Channel<UiState>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _pageCharacter = MutableLiveData(1)
    private val pageCharacter: LiveData<Int> = _pageCharacter

    private val listData = mutableListOf<CharacterUiModel>()

    fun onEvent(event: CharacterEvent) {
        when(event) {
            is CharacterEvent.GetCharacter -> {
                _pageCharacter.value = _pageCharacter.value?.plus(1)
                getServiceCharacter()
            }
        }
    }

    fun getServiceCharacter() {
        viewModelScope.launch {
            _uiEvent.send(element = UiState.Loading)
            usesCases.invoke(pageCharacter.value ?: 1) {
                it.fold(::onServiceError, ::onSuccessService)
            }
        }

    }

    private fun onServiceError(failure: Failure) {
        viewModelScope.launch {
            _uiEvent.send(element = UiState.Error(failure = failure))
        }
    }

    private fun onSuccessService(exampleRequestUiModel: List<CharacterUiModel>) {
        viewModelScope.launch {
            listData.addAll(exampleRequestUiModel)
            _uiEvent.send(element = UiState.Success(list = listData))
        }
    }
}