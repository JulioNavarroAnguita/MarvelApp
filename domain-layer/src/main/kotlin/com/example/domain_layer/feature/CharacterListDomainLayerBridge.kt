package com.example.domain_layer.feature

import arrow.core.Either
import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.base.BaseDomainLayerBridge
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo

const val CHARACTER_LIST_DOMAIN_LAYER_BRIDGE_TAG = "characterListDomainLayerBridge"

interface CharacterListDomainLayerBridge : BaseDomainLayerBridge {
    suspend fun fetchCharacters() : Either<FailureBo, List<CharacterBo>?>
}

class CharacterListDomainLayerBridgeImpl(
    private val fetchCharactersUc: DomainLayerContract.PresentationLayer.UseCase<Any?, List<CharacterBo>?>
) : CharacterListDomainLayerBridge {

    override suspend fun fetchCharacters() : Either<FailureBo, List<CharacterBo>?> =
        fetchCharactersUc.run(null)

}