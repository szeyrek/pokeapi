package com.scz.globallogic.presentation.history

import androidx.fragment.app.viewModels
import com.scz.globallogic.R
import com.scz.globallogic.base.BaseFragment
import com.scz.globallogic.databinding.FragmentPokemonHistoryBinding
import com.scz.globallogic.presentation.HistoryAdapter
import com.scz.globallogic.util.observeNonNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FRPokemonHistory : BaseFragment<FragmentPokemonHistoryBinding, FRPokemonHistoryVM>() {

    override val layoutId: Int = R.layout.fragment_pokemon_history

    override val viewModel: FRPokemonHistoryVM by viewModels()

    private val historyAdapter by lazy {
        HistoryAdapter(listOf())
    }

    override fun initViews() {
        initAdapters()
    }

    private fun initAdapters() {
        binding.adapterHistory = historyAdapter
    }

    override fun onResume() {
        viewModel.getHistory()
        super.onResume()
    }

    override fun observeValues() {
        viewModel.pokemonHistory.observeNonNull(this) {
            historyAdapter.updateData(it)
        }
    }
}