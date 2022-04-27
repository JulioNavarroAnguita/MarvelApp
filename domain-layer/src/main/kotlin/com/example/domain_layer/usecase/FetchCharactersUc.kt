package com.example.domain_layer.usecase

import arrow.core.Either
import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo

const val FETCH_CHARACTERS_UC_TAG = "fetchCharactersUc"

class FetchCharactersUc(private val characterRepository: DomainLayerContract.DataLayer.CharacterRepository) :
    DomainLayerContract.PresentationLayer.UseCase<Any?, List<CharacterBo>> {

    override suspend fun run(params: Any?): Either<FailureBo, List<CharacterBo>> =
        characterRepository.fetchCharacters()

}