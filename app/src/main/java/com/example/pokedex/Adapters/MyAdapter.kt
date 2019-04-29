package com.example.pokedex.Adapters

import com.example.pokedex.Models.Pokemon


object AppConstants{
    val dataset_save_instance_key = "CLE"
    val MAIN_LIST_KEY = "KEY_POKEMON"
}
interface MyPokemonAdapter {
    fun changeDataSet(newDataSet : List<Pokemon>)
}