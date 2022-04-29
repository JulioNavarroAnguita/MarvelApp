package com.example.data_layer.database.dao

import androidx.room.*
import com.example.data_layer.database.entities.CharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_table ORDER BY name ASC")
    suspend fun findAllCharacters() : List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(list: List<CharacterEntity>)

    @Query("DELETE FROM character_table")
    suspend fun deleteAllCharacters()

    @Query("SELECT * FROM character_table WHERE id = :characterId")
    suspend fun findCharacterById(characterId: Int) : CharacterEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    @Query("DELETE FROM character_table WHERE id = :characterId")
    suspend fun deleteCharacterById(characterId: Int)

}