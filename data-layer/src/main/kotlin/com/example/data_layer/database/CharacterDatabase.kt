package com.example.data_layer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_layer.database.dao.CharacterDao
import com.example.data_layer.database.entities.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class CharacterDatabase: RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
}