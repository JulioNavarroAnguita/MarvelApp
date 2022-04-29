package com.example.data_layer.repository

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.data_layer.DataLayerContract
import com.example.data_layer.domain.FailureDto
import com.example.data_layer.domain.characterBoToEntity
import com.example.data_layer.domain.dtoToBo
import com.example.data_layer.domain.toFailureBo
import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo

class CharacterRepositoryImpl(
    private val characterRemoteDataSource: DataLayerContract.CharacterDataSource.Remote,
    private val characterDatabaseDataSource: DataLayerContract.CharacterDataSource.Database,
    private val connectivityDataSource: DataLayerContract.AndroidDataSource
) : DomainLayerContract.DataLayer.CharacterRepository {

    override suspend fun fetchCharacters(): Either<FailureBo, List<CharacterBo>> =
        try {
            connectivityDataSource.checkNetworkConnectionAvailability().takeIf { it }?.let {
                val queryResponse = characterRemoteDataSource.fetchCharactersFromApi()
                if (queryResponse.isSuccessful) {
                    val characterList = queryResponse.body()?.data?.results?.map { it.dtoToBo() }
                    characterList?.let { list ->
                        characterDatabaseDataSource.clearCharacterList()
                        characterDatabaseDataSource.insertCharacterListToDataBase(list.map { it.characterBoToEntity() })
                        list.right()
                    } ?: run {
                        FailureDto.UnexpectedFailure(queryResponse.code(), queryResponse.message()).toFailureBo().left()
                    }
                } else {
                    FailureDto.UnexpectedFailure(queryResponse.code(), queryResponse.message()).toFailureBo().left()
                }
            } ?: run {
                FailureDto.NoNetwork.toFailureBo().left()
            }
        } catch (e: IllegalStateException) {
            Log.e("fetchCharacters", "Error: ${e.message}")
            FailureDto.Unknown.toFailureBo().left()
        }

    override suspend fun getCharactersFromDatabase(): Either<FailureBo, List<CharacterBo>> =
        characterDatabaseDataSource.findCharactersFromDatabase().right()

    override suspend fun fetchCharacterDetail(params: Int): Either<FailureBo, CharacterBo> =
        try {
            connectivityDataSource.checkNetworkConnectionAvailability().takeIf { it }?.let {
                val queryResponse = characterRemoteDataSource.fetchCharacterDetailFromApi(params)
                if (queryResponse.isSuccessful) {
                    val characterDetail = queryResponse.body()?.data?.results?.first()?.dtoToBo()
                    characterDetail?.let { character ->
                        characterDatabaseDataSource.clearCharacter(params)
                        characterDatabaseDataSource.insertCharacterToDataBase(character.characterBoToEntity())
                        character.right()
                    } ?: run {
                        FailureDto.UnexpectedFailure(queryResponse.code(), queryResponse.message()).toFailureBo().left()
                    }
                } else {
                    FailureDto.UnexpectedFailure(queryResponse.code(), queryResponse.message()).toFailureBo().left()
                }
            } ?: run {
                FailureDto.NoNetwork.toFailureBo().left()
            }
        } catch (e: IllegalStateException) {
            Log.e("fetchCharacterDetail", "Error: ${e.message}")
            FailureDto.Unknown.toFailureBo().left()
        }

    override suspend fun getCharacterDetailFromDatabase(params: Int): Either<FailureBo, CharacterBo> =
        characterDatabaseDataSource.findCharacterDetailFromDatabase(params).right()

}
