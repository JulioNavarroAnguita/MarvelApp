package com.example.data_layer.datasource

import com.example.data_layer.DataLayerContract
import com.example.data_layer.database.dao.CharacterDao
import com.example.data_layer.database.entities.CharacterEntity
import com.example.data_layer.domain.*
import com.example.domain_layer.domain.CharacterBo

class CharacterDatabaseDataSource(
    private val characterDao: CharacterDao
): DataLayerContract.CharacterDataSource.Database {

    override suspend fun findCharactersFromDatabase(): List<CharacterBo> =
        characterDao.findAllCharacters().map { it.entityToCharacterBo() }

    override suspend fun insertCharacterListToDataBase(characterList: List<CharacterEntity>) =
        characterDao.insertAllCharacters(characterList)

    override suspend fun clearCharacterList() = characterDao.deleteAllCharacters()

    override suspend fun findCharacterDetailFromDatabase(characterId: Int): CharacterBo =
        characterDao.findCharacterById(characterId).entityToCharacterBo()

    override suspend fun insertCharacterToDataBase(character: CharacterEntity) =
        characterDao.insertCharacter(character)

    override suspend fun clearCharacter(characterId: Int) =
        characterDao.deleteCharacterById(characterId)

}