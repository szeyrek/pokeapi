package com.scz.globallogic.presentation.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.scz.globallogic.R
import com.scz.globallogic.base.BaseFragment
import com.scz.globallogic.databinding.FragmentPokemonDetailBinding
import com.scz.globallogic.presentation.MoveAdapter
import com.scz.globallogic.presentation.StatAdapter
import com.scz.globallogic.util.observeNonNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FRPokemonDetail : BaseFragment<FragmentPokemonDetailBinding, FRPokemonDetailVM>() {

    private val args: FRPokemonDetailArgs by navArgs()

    private val movesAdapter by lazy {
        MoveAdapter(listOf())
    }

    private val statsAdapter by lazy {
        StatAdapter(listOf())
    }
    override val layoutId: Int = R.layout.fragment_pokemon_detail

    override val viewModel: FRPokemonDetailVM by viewModels()

    override fun initViews() {
        initAdapters()
        viewModel.getPokemon(args.id)
        setTitle(args.pokemonName)
    }

    private fun initAdapters() {
        binding.rvMoves.layoutManager = GridLayoutManager(context, 3)
        binding.adapterMove = movesAdapter
        binding.adapterStat = statsAdapter
    }

    override fun observeValues() {
        viewModel.pokemon.observeNonNull(this) { pokemonDetail ->
            pokemonDetail.moves?.let {
                movesAdapter.updateData(it)
            }
            pokemonDetail.stats?.let {
                statsAdapter.updateData(it)
            }
        }
    }
}