package com.example.domain_layer.di

import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.feature.CharacterDetailDomainLayerBridge
import com.example.domain_layer.feature.CharacterDetailDomainLayerBridgeImpl
import com.example.domain_layer.feature.CharacterListDomainLayerBridge
import com.example.domain_layer.feature.CharacterListDomainLayerBridgeImpl
import com.example.domain_layer.usecase.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val bridgeModule = module {
    //bridge
    factory<CharacterListDomainLayerBridge> {
        CharacterListDomainLayerBridgeImpl(
            fetchCharactersUc = get(named(name = FETCH_CHARACTERS_UC_TAG)),
            getCharactersFromDataBaseUc = get(named(name = GET_CHARACTERS_FROM_DATABASE_UC_TAG))
        )
    }
    factory<CharacterDetailDomainLayerBridge> {
        CharacterDetailDomainLayerBridgeImpl(
            fetchCharacterUc = get(named(name = FETCH_CHARACTER_DETAIL_UC_TAG)),
            getCharacterFromDatabaseUc = get(named(name = GET_CHARACTER_DETAIL_FROM_DATABASE_UC_TAG)),
        )
    }
}

val useCaseModule = module {
    //usecase
    factory<DomainLayerContract.PresentationLayer.UseCase<Any?, List<CharacterBo>>>(named(name = FETCH_CHARACTERS_UC_TAG)) {
        FetchCharactersUc(characterRepository = get())
    }
    factory<DomainLayerContract.PresentationLayer.UseCase<Int, CharacterBo>>(named(name = FETCH_CHARACTER_DETAIL_UC_TAG)) {
        FetchCharacterDetailUc(characterRepository = get())
    }
    factory<DomainLayerContract.PresentationLayer.UseCase<Any?, List<CharacterBo>>>(named(name = GET_CHARACTERS_FROM_DATABASE_UC_TAG)) {
        GetCharactersFromDatabaseUc(characterRepository = get())
    }
    factory<DomainLayerContract.PresentationLayer.UseCase<Int, CharacterBo>>(named(name = GET_CHARACTER_DETAIL_FROM_DATABASE_UC_TAG)) {
        GetCharacterFromDatabase(characterRepository = get())
    }

}

val domainLayerModule = listOf(bridgeModule, useCaseModule)