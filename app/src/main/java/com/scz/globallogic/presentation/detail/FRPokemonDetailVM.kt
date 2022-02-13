package com.scz.globallogic.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scz.globallogic.base.BaseViewModel
import com.scz.globallogic.domain.pokemon.GetPokemonUseCase
import com.scz.globallogic.domain.uimodel.PokemonUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FRPokemonDetailVM @Inject constructor(private val getPokemonUseCase: GetPokemonUseCase) :
    BaseViewModel() {

    private val _pokemon = MutableLiveData<PokemonUI>()
    val pokemon: LiveData<PokemonUI> = _pokemon

    fun getPokemon(id: Int) = vmScope.launch {
        getPokemonUseCase(GetPokemonUseCase.Params(id)).flowCall {
            _pokemon.postValue(it)
        }
    }

}