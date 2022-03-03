package com.example.presentation_layer.feature.ui.characterList.viewmodel

import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo
import com.example.domain_layer.feature.characterList.CharacterListDomainLayerBridge
import com.example.presentation_layer.base.BaseMvvmViewModel
import com.example.presentation_layer.base.ScreenState
import com.example.presentation_layer.domain.CharacterVo
import com.example.presentation_layer.feature.ui.characterList.view.state.CharacterListState
import com.example.presentation_layer.domain.characterBoToVo
import com.example.presentation_layer.domain.toVo

class CharacterListViewModel(bridge: CharacterListDomainLayerBridge) :
        BaseMvvmViewModel<CharacterListDomainLayerBridge, CharacterListState>(bridge = bridge) {

    fun loadCharacters() {
            _screenState.value = ScreenState.Loading
            bridge.fetchCharacters(scope = this, onResult = {
                it.fold(::handleError, ::handleSuccess)
            })
    }

    private fun handleSuccess(list: List<CharacterBo>?) {
        _screenState.value = if (list.isNullOrEmpty()){
            ScreenState.Render(CharacterListState.NoData)
        } else {
            ScreenState.Render(CharacterListState.ShowCharacterList(list.characterBoToVo()))

        }
    }

    private fun handleError(failureBo: FailureBo) {
        _screenState.value = ScreenState.Render(CharacterListState.ShowError(failure = failureBo.toVo()))
    }

    fun onCharacterItemClicked(item: CharacterVo) {
        _screenState.value = ScreenState.Render(CharacterListState.ShowCharacterDetail(item))
    }
}
