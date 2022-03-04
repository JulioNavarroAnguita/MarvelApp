package com.example.domain_layer.di

import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.DomainLayerContract.DataLayer.Companion.CHARACTER_REPOSITORY_TAG
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.feature.characterList.CHARACTER_LIST_DOMAIN_LAYER_BRIDGE_TAG
import com.example.domain_layer.feature.characterList.CharacterListDomainLayerBridge
import com.example.domain_layer.feature.characterList.CharacterListDomainLayerBridgeImpl
import com.example.domain_layer.usecase.FETCH_CHARACTERS_UC_TAG
import com.example.domain_layer.usecase.FetchCharactersUc
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainLayerModule = module(override = true) {
    //bridge
    factory<CharacterListDomainLayerBridge>(named(name = CHARACTER_LIST_DOMAIN_LAYER_BRIDGE_TAG)) {
        CharacterListDomainLayerBridgeImpl(
            fetchCharactersUc = get(named(name = FETCH_CHARACTERS_UC_TAG))
        )
    }
    //usecase
    factory<DomainLayerContract.PresentationLayer.UseCase<Any?, List<CharacterBo>?>>(named(name = FETCH_CHARACTERS_UC_TAG)) {
        FetchCharactersUc(characterRepository = get(named(name = CHARACTER_REPOSITORY_TAG)))
    }
}
