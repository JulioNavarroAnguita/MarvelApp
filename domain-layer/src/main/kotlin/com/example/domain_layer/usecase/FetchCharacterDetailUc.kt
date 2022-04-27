package com.example.domain_layer.usecase

import arrow.core.Either
import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo

const val FETCH_CHARACTER_DETAIL_UC_TAG = "fetchCharacterDetailUc"

class FetchCharacterDetailUc(private val characterRepository: DomainLayerContract.DataLayer.CharacterRepository) :
    DomainLayerContract.PresentationLayer.UseCase<Int, CharacterBo> {

    override suspend fun run(params: Int): Either<FailureBo, CharacterBo> =
        characterRepository.fetchCharacterDetail(params)


}