package com.scz.globallogic.repository

import com.scz.globallogic.base.BaseRepository
import com.scz.globallogic.network.PokeService
import com.scz.globallogic.room.PokemonDao
import com.scz.globallogic.room.model.PokemonEntity
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class PokeRepository @Inject constructor(
    private val service: PokeService,
    private val dao: PokemonDao
) : BaseRepository() {

    fun getPokemonForms(id: Int) = safeFlowCall {
        service.getPokemonForm(id)
    }

    fun getPokemon(id: Int) = safeFlowCall {
        service.getPokemon(id)
    }

    fun getPokemons() = safeFlowCall(Dispatchers.Default) {
        dao.getPokemons()
    }

    fun insertPokemon(pokemon: PokemonEntity) = safeFlowCall(Dispatchers.Default) {
        dao.insertPokemon(pokemon)
    }

    fun deletePokemonHistory() = safeFlowCall(Dispatchers.Default) {
        dao.deleteAll()
    }
}