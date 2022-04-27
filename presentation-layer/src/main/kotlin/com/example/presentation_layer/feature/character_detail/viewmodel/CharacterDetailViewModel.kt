package com.example.presentation_layer.feature.character_detail.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo
import com.example.domain_layer.feature.CharacterDetailDomainLayerBridge
import com.example.presentation_layer.base.BaseMvvmViewModel
import com.example.presentation_layer.base.ScreenState
import com.example.presentation_layer.domain.boToVo
import com.example.presentation_layer.domain.toVo
import com.example.presentation_layer.feature.character_detail.view.state.CharacterDetailState
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    bridge: CharacterDetailDomainLayerBridge
) : BaseMvvmViewModel<CharacterDetailDomainLayerBridge, CharacterDetailState>(bridge = bridge) {

    fun loadCharacterDetail(characterId: Int) {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            bridge.fetchCharacterDetail(params = characterId).fold(::handleError, ::handleSuccess)
        }
    }

    private fun handleSuccess(character: CharacterBo) {
        _screenState.value =
            ScreenState.Render(CharacterDetailState.ShowCharacterDetail(character.boToVo()))
    }

    private fun handleError(failureBo: FailureBo) {
        _screenState.value =
            ScreenState.Render(CharacterDetailState.ShowError(failure = failureBo.toVo()))
    }

}
