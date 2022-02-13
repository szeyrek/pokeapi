package com.scz.globallogic.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scz.globallogic.base.BaseViewModel
import com.scz.globallogic.repository.PokeRepository
import com.scz.globallogic.room.model.PokemonEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FRPokemonHistoryVM @Inject constructor(private val repository: PokeRepository) :
    BaseViewModel() {

    private val _pokemonHistory = MutableLiveData<List<PokemonEntity>>()
    val pokemonHistory: LiveData<List<PokemonEntity>> = _pokemonHistory

    fun getHistory() {
        vmScope.launch {
            repository.getPokemons().flowCall {
                _pokemonHistory.postValue(it)
            }
        }
    }

    fun onClickedClearHistory() {
        vmScope.launch {
            repository.deletePokemonHistory().flowCall {
                getHistory()
            }
        }
    }
}