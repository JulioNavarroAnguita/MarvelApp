package com.example.domain_layer.di

import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.DomainLayerContract.DataLayer.Companion.CHARACTER_REPOSITORY_TAG
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.feature.CHARACTER_DETAIL_DOMAIN_LAYER_BRIDGE_TAG
import com.example.domain_layer.feature.CharacterDetailDomainLayerBridge
import com.example.domain_layer.feature.CharacterDetailDomainLayerBridgeImpl
import com.example.domain_layer.feature.CHARACTER_LIST_DOMAIN_LAYER_BRIDGE_TAG
import com.example.domain_layer.feature.CharacterListDomainLayerBridge
import com.example.domain_layer.feature.CharacterListDomainLayerBridgeImpl
import com.example.domain_layer.usecase.FETCH_CHARACTERS_UC_TAG
import com.example.domain_layer.usecase.FETCH_CHARACTER_DETAIL_UC_TAG
import com.example.domain_layer.usecase.FetchCharacterDetailUc
import com.example.domain_layer.usecase.FetchCharactersUc
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainLayerModule = module {
    //bridge
    factory<CharacterListDomainLayerBridge>(named(name = CHARACTER_LIST_DOMAIN_LAYER_BRIDGE_TAG)) {
        CharacterListDomainLayerBridgeImpl(
            fetchCharactersUc = get(named(name = FETCH_CHARACTERS_UC_TAG))
        )
    }
    factory<CharacterDetailDomainLayerBridge>(named(name = CHARACTER_DETAIL_DOMAIN_LAYER_BRIDGE_TAG)) {
        CharacterDetailDomainLayerBridgeImpl(
            fetchCharacterUc = get(named(name = FETCH_CHARACTER_DETAIL_UC_TAG))
        )
    }
    //usecase
    factory<DomainLayerContract.PresentationLayer.UseCase<Any?, List<CharacterBo>>>(named(name = FETCH_CHARACTERS_UC_TAG)) {
        FetchCharactersUc(characterRepository = get(named(name = CHARACTER_REPOSITORY_TAG)))
    }
    factory<DomainLayerContract.PresentationLayer.UseCase<Int, CharacterBo>>(named(name = FETCH_CHARACTER_DETAIL_UC_TAG)) {
        FetchCharacterDetailUc(characterRepository = get(named(name = CHARACTER_REPOSITORY_TAG)))
    }

}
