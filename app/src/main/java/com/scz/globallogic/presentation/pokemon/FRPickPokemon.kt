package com.scz.globallogic.presentation.pokemon

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.scz.globallogic.R
import com.scz.globallogic.base.BaseFragment
import com.scz.globallogic.databinding.FragmentPickPokemonBinding
import com.scz.globallogic.util.observeNonNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FRPickPokemon : BaseFragment<FragmentPickPokemonBinding, FRPickPokemonVM>() {

    override val layoutId: Int = R.layout.fragment_pick_pokemon

    override val viewModel: FRPickPokemonVM by viewModels()

    override fun setListeners() {
        with(binding) {
            teEditText.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty()) {
                    viewModel?.setInputId(-1)
                    return@doOnTextChanged
                }
                val id = text.toString().toInt()
                tilId.error = if (id > 898) {
                    viewModel?.setInputId(-1)
                    getString(R.string.pokemon_form_002)
                } else {
                    viewModel?.setInputId(id)
                    null
                }
            }
        }
    }

    override fun observeValues() {
        viewModel.onClickedMore.observeNonNull(this) {
            it.getContentIfNotHandled()
                ?.let {
                    navigate(
                        FRPickPokemonDirections.toFRPokemonDetail(
                            it.id ?: -1,
                            it.name ?: ""
                        )
                    )
                }
        }
    }
}