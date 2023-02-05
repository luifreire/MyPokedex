package com.example.mypokedex.pokemon.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.databinding.PokedexListItemBinding

class PokedexListAdapter(var names: List<String>): RecyclerView.Adapter<PokedexListAdapter.PokemonListAdapterViewHolder>(){
    inner class PokemonListAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
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
            binding.nametv.text = names[position]
            binding.numbertv.text = (position + 1).toString()
        }
    }
}