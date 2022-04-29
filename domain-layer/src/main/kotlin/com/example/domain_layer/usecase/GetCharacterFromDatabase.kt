package com.example.domain_layer.usecase

import arrow.core.Either
import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo

const val GET_CHARACTER_DETAIL_FROM_DATABASE_UC_TAG = "getCharacterDetailFromDatabaseUc"

class GetCharacterFromDatabase(private val characterRepository: DomainLayerContract.DataLayer.CharacterRepository) :
    DomainLayerContract.PresentationLayer.UseCase<Int, CharacterBo> {

    override suspend fun run(params: Int): Either<FailureBo, CharacterBo> =
        characterRepository.getCharacterDetailFromDatabase(params)

}