package com.scz.globallogic.presentation

import com.scz.globallogic.R
import com.scz.globallogic.base.BaseAdapter
import com.scz.globallogic.base.BaseViewHolder
import com.scz.globallogic.databinding.ItemPokemonHistoryBinding
import com.scz.globallogic.databinding.ItemPokemonMoveBinding
import com.scz.globallogic.databinding.ItemPokemonStatBinding
import com.scz.globallogic.domain.uimodel.MoveListItemUI
import com.scz.globallogic.domain.uimodel.StatListItemUI
import com.scz.globallogic.room.model.PokemonEntity


class MoveAdapter(data: List<MoveListItemUI>) :
    BaseAdapter<ItemPokemonMoveBinding, MoveListItemUI>(data) {
    override val layoutId: Int = R.layout.item_pokemon_move
}

class StatAdapter(data: List<StatListItemUI>) :
    BaseAdapter<ItemPokemonStatBinding, StatListItemUI>(data) {
    override val layoutId: Int = R.layout.item_pokemon_stat
}

class HistoryAdapter(data: List<PokemonEntity>) :
    BaseAdapter<ItemPokemonHistoryBinding, PokemonEntity>(data) {
    override val layoutId: Int = R.layout.item_pokemon_history

}