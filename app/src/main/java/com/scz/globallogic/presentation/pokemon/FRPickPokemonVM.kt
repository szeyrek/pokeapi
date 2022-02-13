package com.scz.globallogic.presentation.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scz.globallogic.base.BaseViewModel
import com.scz.globallogic.domain.pokemonform.GetPokemonFormUseCase
import com.scz.globallogic.domain.uimodel.PokemonFormUI
import com.scz.globallogic.repository.PokeRepository
import com.scz.globallogic.room.model.PokemonEntity
import com.scz.globallogic.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FRPickPokemonVM @Inject constructor(
    private val getPokemonFormUseCase: GetPokemonFormUseCase,
    private val repository: PokeRepository
) :
    BaseViewModel() {

    init {
        getPokemonForms((1..898).random())
    }

    private val _pokemonForm = MutableLiveData<PokemonFormUI>()
    val pokemonForm: LiveData<PokemonFormUI> = _pokemonForm

    private val _inputId = MutableLiveData(-1)
    val inputId: LiveData<Int> = _inputId

    private val _onClickedMore = MutableLiveData<Event<PokemonFormUI>>()
    val onClickedMore: LiveData<Event<PokemonFormUI>> = _onClickedMore

    private fun getPokemonForms(id: Int) = vmScope.launch {
        getPokemonFormUseCase(GetPokemonFormUseCase.Params(id)).flowCall {
            _pokemonForm.postValue(it)
            insertToHistory(it.id, it.name)
        }
    }

    private fun insertToHistory(id: Int?, name: String?) = vmScope.launch {
        repository.insertPokemon(PokemonEntity(id, name)).flowCall(showLoaderView = false) {

        }
    }

    fun onClickedPickPokemon() {
        getPokemonForms((1..898).random())
    }

    fun onClickedPickPokemonWithId() {
        inputId.value?.let { getPokemonForms(it) }
    }

    fun onClickedMore() {
        pokemonForm.value?.let {
            _onClickedMore.postValue(Event(it))
        }
    }

    fun setInputId(id: Int) {
        _inputId.postValue(id)
    }
}