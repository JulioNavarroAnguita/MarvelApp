package com.example.presentation_layer.feature.character_list.view.state

import com.example.presentation_layer.base.BaseState
import com.example.presentation_layer.domain.CharacterVo
import com.example.presentation_layer.domain.FailureVo

sealed class CharacterListState : BaseState() {
    class ShowCharacterList(val characterList: List<CharacterVo>) : CharacterListState()
    class ShowCharacterDetail(val character: CharacterVo) : CharacterListState()
    class ShowError(val failure: FailureVo) : CharacterListState()
    object NoData : CharacterListState()
}
