package com.scz.globallogic.domain.pokemon

import com.scz.globallogic.base.BaseUseCase
import com.scz.globallogic.domain.uimodel.*
import com.scz.globallogic.network.model.*
import com.scz.globallogic.repository.PokeRepository
import com.scz.globallogic.util.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val repository: PokeRepository) :
    BaseUseCase<GetPokemonUseCase.Params, PokemonUI>() {

    data class Params(
        val id: Int
    )

    override fun execute(params: Params): Flow<PokemonUI> =
        repository.getPokemon(params.id).map(::response2UI)

    private fun response2UI(response: PokemonResponse): PokemonUI {
        return object : Mapper<PokemonResponse, PokemonUI>() {
            override fun map(value: PokemonResponse): PokemonUI {
                return PokemonUI(
                    responseMoveListItem2UI(value.moves),
                    responseStatListItem2UI(value.stats)
                )
            }
        }.map(response)
    }

    private fun responseMoveListItem2UI(moves: List<MoveListItem>?): List<MoveListItemUI> {
        moves?.let {
            return object : Mapper<MoveListItem, MoveListItemUI>() {
                override fun map(value: MoveListItem): MoveListItemUI {
                    return MoveListItemUI(responseMove2UI(value.move))
                }
            }.map(it)
        } ?: return run { listOf() }
    }

    private fun responseMove2UI(move: Move?): MoveUI {
        move?.let {
            return object : Mapper<Move, MoveUI>() {
                override fun map(value: Move): MoveUI {
                    return MoveUI(value.name?.replaceFirstChar { it.uppercase() })
                }
            }.map(it)
        } ?: return run { MoveUI("") }
    }

    private fun responseStatListItem2UI(stats: List<StatListItem>?): List<StatListItemUI> {
        stats?.let {
            return object : Mapper<StatListItem, StatListItemUI>() {
                override fun map(value: StatListItem): StatListItemUI {
                    return StatListItemUI(
                        value.baseStat.toString(),
                        value.effort.toString(),
                        responseStat2UI(value.stat)
                    )
                }
            }.map(it)
        } ?: return run { listOf() }
    }

    private fun responseStat2UI(stat: Stat?): StatUI {
        stat?.let {
            return object : Mapper<Stat, StatUI>() {
                override fun map(value: Stat): StatUI {
                    return StatUI(value.name?.replaceFirstChar { it.uppercase() })
                }
            }.map(it)
        } ?: return run { StatUI("") }
    }
}