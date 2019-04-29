package com.example.pokedex.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokedex.Adapters.AppConstants
import com.example.pokedex.Adapters.MyPokemonAdapter
import com.example.pokedex.Adapters.PokemonAdapter
import com.example.pokedex.Adapters.PokemonLandscapeAdapter
import com.example.pokedex.R
import com.example.pokedex.Models.Pokemon
import kotlinx.android.synthetic.main.fragment_pokedex_list.*
import kotlinx.android.synthetic.main.fragment_pokedex_list.view.*
import java.lang.ClassCastException

class pokedexFragment : Fragment() {

    private lateinit var pokemons : ArrayList<Pokemon>
    private lateinit var pokemonAdapter: MyPokemonAdapter
    var listenerTool : SearchPokemonListener?= null

    companion object{
        fun newInstance(dataset: ArrayList<Pokemon>): pokedexFragment{
            val newFragment = pokedexFragment()
            newFragment.pokemons = dataset
            return newFragment
        }
    }

    interface SearchPokemonListener{
        fun managePortraitItemClick(pokemon: Pokemon)
        fun searchPokemonByType(typeName: String)
        fun manageLandscapeItemClick(pokemon: Pokemon)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_pokedex_list, container, false)

        if(savedInstanceState != null) pokemons = savedInstanceState.getParcelableArrayList<Pokemon>(AppConstants.MAIN_LIST_KEY)!!

        initRecyclerView(resources.configuration.orientation, view)
        initSearchButton(view)

        return view
    }

    fun initRecyclerView(orientation:Int, container:View){
        val linearLayoutManager = LinearLayoutManager(this.context)

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            pokemonAdapter = PokemonAdapter(pokemons, {pokemon:Pokemon->listenerTool?.managePortraitItemClick(pokemon)})
            container.list.adapter = pokemonAdapter as PokemonAdapter
            container.list.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this.context,2)
            }
        }
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            pokemonAdapter = PokemonAdapter(pokemons, { pokemon:Pokemon->listenerTool?.manageLandscapeItemClick(pokemon)})
            container.list.adapter = pokemonAdapter as PokemonAdapter
            container.list.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this.context,1)
            }
        }
    }

    fun initSearchButton(container:View) = container.btn_pokemon_search.setOnClickListener {
        listenerTool?.searchPokemonByType(et_type.text.toString())
    }

    fun updateMoviesAdapter(movieList: ArrayList<Pokemon>){ pokemonAdapter.changeDataSet(movieList) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchPokemonListener) {
            listenerTool = context
        } else {
            throw ClassCastException("Se necesita una implementacion de  la interfaz")
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.MAIN_LIST_KEY, pokemons)
        super.onSaveInstanceState(outState)
    }

    override fun onDetach() {
        super.onDetach()
        listenerTool = null
    }

}
