package com.scz.globallogic.network.model

import com.squareup.moshi.Json

data class PokemonResponse(
    val id: Int?,
    val moves: List<MoveListItem>?,
    val stats: List<StatListItem>?
)

data class MoveListItem(
    val move: Move?
)

data class Move(
    val name: String?
)

data class StatListItem(
    @field:Json(name = "base_stat")
    val baseStat: Int?,
    val effort: Int?,
    val stat: Stat?
)

data class Stat(
    val name: String?
)