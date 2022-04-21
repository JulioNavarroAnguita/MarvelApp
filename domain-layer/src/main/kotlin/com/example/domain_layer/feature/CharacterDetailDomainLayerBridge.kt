package com.example.domain_layer.feature

import arrow.core.Either
import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.base.BaseDomainLayerBridge
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo

const val CHARACTER_DETAIL_DOMAIN_LAYER_BRIDGE_TAG = "characterDetailDomainLayerBridge"

interface CharacterDetailDomainLayerBridge : BaseDomainLayerBridge {
    suspend fun fetchCharacterDetail(params: Int): Either<FailureBo, CharacterBo>
}

class CharacterDetailDomainLayerBridgeImpl(
    private val fetchCharacterUc: DomainLayerContract.PresentationLayer.UseCase<Int, CharacterBo>
) : CharacterDetailDomainLayerBridge {

    override suspend fun fetchCharacterDetail(params: Int): Either<FailureBo, CharacterBo> =
        fetchCharacterUc.run(params = params)

}