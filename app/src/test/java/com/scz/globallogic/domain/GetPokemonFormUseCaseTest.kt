package com.scz.globallogic.domain

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.scz.globallogic.domain.pokemonform.GetPokemonFormUseCase
import com.scz.globallogic.network.model.PokemonFormResponse
import com.scz.globallogic.repository.PokeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

class GetPokemonFormUseCaseTest {

    private lateinit var useCase: GetPokemonFormUseCase

    @MockK
    private lateinit var repository: PokeRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetPokemonFormUseCase(repository)
    }

    @ExperimentalTime
    @Test
    fun `when pokemon forms requested should return pokemon form`() = runBlocking {
        //Given
        coEvery { repository.getPokemonForms(any()) } coAnswers { flow { emit(response) } }

        //When
        useCase(GetPokemonFormUseCase.Params(1)).test {
            //Then
            val item = expectItem()
            Truth.assertThat(item.id).isEqualTo(1)
            Truth.assertThat(item.name).isEqualTo("Bulbasaur")
            expectComplete()
        }
    }

    private val response = PokemonFormResponse(
        id = 1,
        name = "bulbasaur",
        null,
    )
}