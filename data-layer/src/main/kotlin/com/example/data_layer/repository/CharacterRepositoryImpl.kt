package com.example.data_layer.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.data_layer.DataLayerContract
import com.example.data_layer.domain.FailureDto
import com.example.data_layer.domain.dtoToBo
import com.example.data_layer.domain.toFailureBo
import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo

class CharacterRepositoryImpl(
    private val characterDataSource: DataLayerContract.CharacterDataSource
) : DomainLayerContract.DataLayer.CharacterRepository {

    override suspend fun fetchCharacters(): Either<FailureBo, List<CharacterBo>> {
        val queryResponse = characterDataSource.fetchCharacters()
        return if (queryResponse.isSuccessful) {
            val characterList = queryResponse.body()?.data?.results?.map { it.dtoToBo() }
            characterList?.let { characterList.right() } ?: run{ FailureDto.UnexpectedFailure(queryResponse.code(),  queryResponse.message()).toFailureBo().left() }
        } else {
            FailureDto.UnexpectedFailure(queryResponse.code(),  queryResponse.message()).toFailureBo().left()
        }
    }

    override suspend fun fetchCharacterDetail(params: Int): Either<FailureBo, CharacterBo> {
        val queryResponse = characterDataSource.fetchCharacterDetail(params)
        return if (queryResponse.isSuccessful) {
            val characterDetail = queryResponse.body()?.data?.results?.first()?.dtoToBo()
            characterDetail?.let { characterDetail.right() } ?: run{ FailureDto.UnexpectedFailure(queryResponse.code(),  queryResponse.message()).toFailureBo().left() }
        } else {
            FailureDto.UnexpectedFailure(queryResponse.code(),  queryResponse.message()).toFailureBo().left()
        }
    }

}
