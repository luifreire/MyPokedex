package com.example.mypokedex.pokemon.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.databinding.PokedexListItemBinding

class PokedexListAdapter(var names: List<String>): RecyclerView.Adapter<PokedexListAdapter.PokemonListAdapterViewHolder>(){
    var onItemClick:((String) -> Unit)? = null
    inner class PokemonListAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onItemClick?.invoke(names[adapterPosition]) }
        }
    }
    private lateinit var binding: PokedexListItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonListAdapterViewHolder {
        binding = PokedexListItemBinding.inflate(LayoutInflater.from(parent.context))
        return PokemonListAdapterViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: PokemonListAdapterViewHolder, position: Int) {
        holder.itemView.apply {
            binding.nametv.text = names[position].capitalize()
            binding.numbertv.text = (position + 1).toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}