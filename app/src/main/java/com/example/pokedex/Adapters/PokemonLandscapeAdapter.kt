package com.example.pokedex.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.Models.Pokemon
import kotlinx.android.synthetic.main.fragment_pokedex.view.*

class PokemonLandscapeAdapter(var pokemon: List<Pokemon>, val clickListener: (Pokemon) -> Unit): RecyclerView.Adapter<PokemonLandscapeAdapter.ViewHolder>(), MyPokemonAdapter {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(pokemon[position], clickListener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_pokedex, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = pokemon.size

    override fun changeDataSet(newDataSet: List<Pokemon>) {
        this.pokemon = newDataSet
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: Pokemon, clickListener: (Pokemon) -> Unit) = with(itemView){
            Glide.with(itemView.context)
                .load(item.sprites)
                .placeholder(R.drawable.ic_launcher_background)
                .into(iv_pokemon_image_frag)
            tv_pokemon_id_frag.text = item.id.toString()
            tv_pokemon_name_frag.text = item.name

            this.setOnClickListener { clickListener(item) }
        }
    }
}