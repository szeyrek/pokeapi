package com.scz.globallogic.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scz.globallogic.room.model.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}