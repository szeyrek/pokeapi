package com.scz.globallogic.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scz.globallogic.room.model.PokemonEntity

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemons")
    suspend fun getPokemons(): List<PokemonEntity>

    @Query("DELETE FROM pokemons")
    suspend fun deleteAll()

}