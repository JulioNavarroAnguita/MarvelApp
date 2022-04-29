package com.example.data_layer

import com.example.data_layer.database.entities.CharacterEntity
import com.example.data_layer.domain.CharacterDto
import com.example.data_layer.domain.ResponseMarvelDto
import com.example.domain_layer.domain.CharacterBo
import retrofit2.Response

interface DataLayerContract {

    interface CharacterDataSource {
        interface Remote {
            suspend fun fetchCharactersFromApi(): Response<ResponseMarvelDto<CharacterDto>>
            suspend fun fetchCharacterDetailFromApi(characterId: Int): Response<ResponseMarvelDto<CharacterDto>>
        }
        interface Database {
            suspend fun findCharactersFromDatabase(): List<CharacterBo>
            suspend fun insertCharacterListToDataBase(characterList: List<CharacterEntity>)
            suspend fun clearCharacterList()
            suspend fun findCharacterDetailFromDatabase(characterId: Int): CharacterBo
            suspend fun insertCharacterToDataBase(character: CharacterEntity)
            suspend fun clearCharacter(characterId: Int)
        }
    }
    interface AndroidDataSource {
        suspend fun checkNetworkConnectionAvailability(): Boolean
    }

}