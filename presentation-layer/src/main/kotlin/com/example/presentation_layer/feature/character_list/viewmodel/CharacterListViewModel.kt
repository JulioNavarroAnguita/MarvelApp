package com.example.presentation_layer.feature.character_list.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo
import com.example.domain_layer.feature.CharacterListDomainLayerBridge
import com.example.presentation_layer.base.BaseMvvmViewModel
import com.example.presentation_layer.base.ScreenState
import com.example.presentation_layer.domain.CharacterVo
import com.example.presentation_layer.domain.characterBoToVo
import com.example.presentation_layer.domain.toVo
import com.example.presentation_layer.feature.character_list.view.state.CharacterListState
import kotlinx.coroutines.launch

class CharacterListViewModel(bridge: CharacterListDomainLayerBridge) :
    BaseMvvmViewModel<CharacterListDomainLayerBridge, CharacterListState>(bridge = bridge) {

    fun loadCharacters() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            bridge.fetchCharacters().fold(::handleError, ::handleSuccess)
        }
    }

    fun onCharacterItemClicked(item: CharacterVo) {
        _screenState.value = ScreenState.Render(CharacterListState.ShowCharacterDetail(item))
    }

    fun loadCharactersFromDatabase() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            bridge.getCharactersFromDataBase().fold(::handleError, ::getCharactersFromDatabaseSuccess)
        }
    }

    private fun handleSuccess(list: List<CharacterBo>?) {
        if (list.isNullOrEmpty()) {
            loadCharactersFromDatabase()
        } else {
            _screenState.value = ScreenState.Render(CharacterListState.ShowCharacterList(list.characterBoToVo()))
        }
    }

    private fun getCharactersFromDatabaseSuccess(list: List<CharacterBo>) {
        _screenState.value = if (list.isNullOrEmpty()) {
            ScreenState.Render(CharacterListState.NoData)
        } else {
            ScreenState.Render(CharacterListState.ShowCharacterList(list.characterBoToVo()))
        }
    }

    private fun handleError(failureBo: FailureBo) {
        _screenState.value =
            ScreenState.Render(CharacterListState.ShowError(failure = failureBo.toVo()))
    }

}
