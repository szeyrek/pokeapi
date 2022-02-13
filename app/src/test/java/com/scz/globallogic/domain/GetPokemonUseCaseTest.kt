package com.scz.globallogic.domain

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.scz.globallogic.domain.pokemon.GetPokemonUseCase
import com.scz.globallogic.network.model.*
import com.scz.globallogic.repository.PokeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

class GetPokemonUseCaseTest {

    private lateinit var useCase: GetPokemonUseCase

    @MockK
    private lateinit var repository: PokeRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetPokemonUseCase(repository)
    }

    @ExperimentalTime
    @Test
    fun `when pokemon requested should return pokemon moves and stats`() = runBlocking {
        //Given
        coEvery { repository.getPokemon(any()) } coAnswers { flow { emit(response) } }
        //When
        useCase(GetPokemonUseCase.Params(1)).test {
            //Then
            val item = expectItem()
            Truth.assertThat(item.moves?.get(0)?.move?.name).isEqualTo("Punch")
            Truth.assertThat(item.stats?.get(0)?.baseStat).isEqualTo("10")
            Truth.assertThat(item.stats?.get(0)?.effort).isEqualTo("1")
            Truth.assertThat(item.stats?.get(0)?.stat?.name).isEqualTo("Hp")
            expectComplete()
        }
    }

    private val response = PokemonResponse(
        1,
        listOf(MoveListItem(Move("punch"))),
        listOf(StatListItem(10, 1, Stat("Hp")))
    )
}