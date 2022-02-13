package com.scz.globallogic.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pokemons")
data class PokemonEntity(
    val pokemonId: Int?,
    val pokemonName: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)