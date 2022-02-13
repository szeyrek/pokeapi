package com.scz.globallogic.network.model

import com.squareup.moshi.Json

data class PokemonFormResponse(
    val id: Int?,
    val name: String?,
    val sprites: Sprites?
)

data class Sprites(
    @field:Json(name = "front_default")
    val frontDefault: String?,
    @field:Json(name = "back_default")
    val backDefault: String?
)