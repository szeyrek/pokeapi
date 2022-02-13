package com.scz.globallogic.domain.uimodel

data class PokemonUI(
    val moves: List<MoveListItemUI>?,
    val stats: List<StatListItemUI>?
)

data class MoveListItemUI(
    val move: MoveUI?
)

data class MoveUI(
    val name: String?
)

data class StatListItemUI(
    val baseStat: String?,
    val effort: String?,
    val stat: StatUI?
)

data class StatUI(
    val name: String?
)

