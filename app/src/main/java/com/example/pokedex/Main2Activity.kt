package com.example.pokedex

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.pokedex.Models.Pokemon
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val request: Pokemon = intent?.extras?.getParcelable("Pokemon") ?: Pokemon()
        init(request)
    }

    fun init(pokemon: Pokemon){
        Glide.with(this)
            .load(pokemon.sprites)
            .placeholder(R.drawable.ic_launcher_background)
            .into(iv_pokemon_image)
        tv_pokemon_id.text = pokemon.id.toString()
        tv_pokemon_name.text = pokemon.name
        tv_pokemon_height.text = pokemon.height
        tv_pokemon_weight.text = pokemon.weight
        et_pokemon_type1.text = pokemon.fsttype
        et_pokemon_type2.text = pokemon.sndtype
    }
}
