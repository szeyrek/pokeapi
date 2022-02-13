package com.scz.globallogic.domain.pokemonform

import com.scz.globallogic.base.BaseUseCase
import com.scz.globallogic.domain.uimodel.PokemonFormUI
import com.scz.globallogic.network.model.PokemonFormResponse
import com.scz.globallogic.repository.PokeRepository
import com.scz.globallogic.util.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPokemonFormUseCase @Inject constructor(private val repository: PokeRepository) :
    BaseUseCase<GetPokemonFormUseCase.Params, PokemonFormUI>() {

    data class Params(
        val id: Int
    )

    override fun execute(params: Params): Flow<PokemonFormUI> =
        repository.getPokemonForms(params.id).map(::response2UI)

    private fun response2UI(response: PokemonFormResponse): PokemonFormUI {
        return object : Mapper<PokemonFormResponse, PokemonFormUI>() {
            override fun map(value: PokemonFormResponse): PokemonFormUI {
                return PokemonFormUI(
                    value.id,
                    value.name?.replaceFirstChar { it.uppercase() },
                    value.sprites?.frontDefault,
                    value.sprites?.backDefault
                )
            }
        }.map(response)
    }
}