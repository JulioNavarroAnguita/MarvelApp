package com.example.domain_layer.feature.characterList

import arrow.core.Either
import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.base.BaseDomainLayerBridge
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo
import kotlinx.coroutines.CoroutineScope

const val CHARACTER_LIST_DOMAIN_LAYER_BRIDGE_TAG = "characterListDomainLayerBridge"

interface CharacterListDomainLayerBridge : BaseDomainLayerBridge {
    fun fetchCharacters(scope: CoroutineScope, onResult: (Either<FailureBo, List<CharacterBo>?>) -> Unit = {})
}

class CharacterListDomainLayerBridgeImpl(
    private val fetchCharactersUc: DomainLayerContract.PresentationLayer.UseCase<Any?, List<CharacterBo>?>
) : CharacterListDomainLayerBridge {
    override fun fetchCharacters(scope: CoroutineScope, onResult: (Either<FailureBo, List<CharacterBo>?>) -> Unit) {
        fetchCharactersUc.invoke(scope = scope, onResult = onResult)
    }

}