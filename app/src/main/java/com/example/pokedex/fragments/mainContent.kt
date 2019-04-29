package com.example.pokedex.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.Models.Pokemon
import kotlinx.android.synthetic.main.fragment_main_content.view.*
import kotlinx.android.synthetic.main.fragment_pokedex.view.*


class mainContent : Fragment() {
    var pokemon = Pokemon()

    companion object {
        fun newInstance(pokemon: Pokemon): mainContent{
            val newFragment = mainContent()
            newFragment.pokemon = pokemon
            return newFragment
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_main_content, container, false)
        bindData(view)
        return view
    }

    fun bindData(view: View){
        view.tv_pokemon_id_main.text = pokemon.id.toString()
        view.tv_pokemon_name_main.text = pokemon.name
        view.tv_pokemon_height_main.text = pokemon.height
        view.tv_pokemon_weight_main.text = pokemon.weight
        view.et_pokemon_type1_main.text = pokemon.fsttype
        view.et_pokemon_type2_main.text = pokemon.sndtype
        Glide.with(view).load(pokemon.sprites)
            .placeholder(R.drawable.ic_launcher_background)
            .into(view.iv_pokemon_image_main)
    }
}
