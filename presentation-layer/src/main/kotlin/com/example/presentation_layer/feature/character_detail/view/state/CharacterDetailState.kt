package com.example.presentation_layer.feature.character_detail.view.state

import com.example.presentation_layer.base.BaseState
import com.example.presentation_layer.domain.CharacterVo
import com.example.presentation_layer.domain.FailureVo

sealed class CharacterDetailState : BaseState() {
    class ShowCharacterDetail(val character: CharacterVo) : CharacterDetailState()
    class ShowError(val failure: FailureVo) : CharacterDetailState()

}