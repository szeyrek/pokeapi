package com.scz.globallogic.network

import com.scz.globallogic.network.model.PokemonFormResponse
import com.scz.globallogic.network.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {

    @GET("pokemon-form/{id}")
    suspend fun getPokemonForm(@Path("id") id: Int): PokemonFormResponse

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonResponse
}