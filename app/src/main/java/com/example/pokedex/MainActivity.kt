package com.example.pokedex

import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.example.pokedex.Adapters.AppConstants
import com.example.pokedex.Network.NetworkUtils
import com.example.pokedex.Models.Pokemon
import com.example.pokedex.fragments.mainContent
import com.example.pokedex.fragments.pokedexFragment
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity(),pokedexFragment.SearchPokemonListener {

    private lateinit var mainFragment: pokedexFragment
    private lateinit var mainContentFragment: mainContent
    private var typePokemonName:String = ""
    private var pokemonList = ArrayList<Pokemon>()


    override fun managePortraitItemClick(pokemon: Pokemon) {
        val movieBundle = Bundle()
        movieBundle.putParcelable("Pokemon", pokemon)
        startActivity(Intent(this, Main2Activity::class.java).putExtras(movieBundle))
    }

    override fun searchPokemonByType(typeName: String) {
        typePokemonName = typeName.decapitalize()
        when (typePokemonName){
            "flying"->FetchTypesPokemon().execute(typePokemonName)
            "1"->FetchTypesPokemon().execute(typePokemonName)
            "2"->FetchTypesPokemon().execute(typePokemonName)
            "3"->FetchTypesPokemon().execute(typePokemonName)
            "4"->FetchTypesPokemon().execute(typePokemonName)
            "5"->FetchTypesPokemon().execute(typePokemonName)
            "6"->FetchTypesPokemon().execute(typePokemonName)
            "7"->FetchTypesPokemon().execute(typePokemonName)
            "8"->FetchTypesPokemon().execute(typePokemonName)
            "9"->FetchTypesPokemon().execute(typePokemonName)
            "10"->FetchTypesPokemon().execute(typePokemonName)
            "11"->FetchTypesPokemon().execute(typePokemonName)
            "14"->FetchTypesPokemon().execute(typePokemonName)
            "15"->FetchTypesPokemon().execute(typePokemonName)
            "12"->FetchTypesPokemon().execute(typePokemonName)
            "13"->FetchTypesPokemon().execute(typePokemonName)
            "16"->FetchTypesPokemon().execute(typePokemonName)
            "17"->FetchTypesPokemon().execute(typePokemonName)
            "18"->FetchTypesPokemon().execute(typePokemonName)
            "normal"->FetchTypesPokemon().execute(typePokemonName)
            "poison"->FetchTypesPokemon().execute(typePokemonName)
            "steel"->FetchTypesPokemon().execute(typePokemonName)
            "fighting"->FetchTypesPokemon().execute(typePokemonName)
            "dragon"->FetchTypesPokemon().execute(typePokemonName)
            "dark"->FetchTypesPokemon().execute(typePokemonName)
            "bug"->FetchTypesPokemon().execute(typePokemonName)
            "fire"->FetchTypesPokemon().execute(typePokemonName)
            "psychic"->FetchTypesPokemon().execute(typePokemonName)
            "fairy"->FetchTypesPokemon().execute(typePokemonName)
            "rock"->FetchTypesPokemon().execute(typePokemonName)
            "ice"->FetchTypesPokemon().execute(typePokemonName)
            "ghost"->FetchTypesPokemon().execute(typePokemonName)
            "electric"->FetchTypesPokemon().execute(typePokemonName)
            "grass"->FetchTypesPokemon().execute(typePokemonName)
            "ground"->FetchTypesPokemon().execute(typePokemonName)
            "water"->FetchTypesPokemon().execute(typePokemonName)
            else->Toast.makeText(this@MainActivity,"Ha ingresado un tipo no valido (ingresar en ingles o id)",Toast.LENGTH_LONG)
        }

    }

    override fun manageLandscapeItemClick(pokemon: Pokemon) {
        mainContentFragment = mainContent.newInstance(pokemon)
        changeFragment(R.id.land_main_cont_fragment, mainContentFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pokemonList = savedInstanceState?.getParcelableArrayList(AppConstants.dataset_save_instance_key) ?: ArrayList()
        initMainFragment()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.dataset_save_instance_key,pokemonList)
        super.onSaveInstanceState(outState)
    }

    fun initMainFragment(){
        mainFragment = pokedexFragment.newInstance(pokemonList)

        val resource = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            R.id.main_fragment
        else {
            mainContentFragment = mainContent.newInstance(Pokemon())
            changeFragment(R.id.land_main_cont_fragment, mainContentFragment)

            R.id.land_main_fragment
        }

        changeFragment(resource, mainFragment)
    }

    private fun changeFragment(id: Int, frag: Fragment){ supportFragmentManager.beginTransaction().replace(id, frag).commit() }

    fun addPokemonToList(pokemon: Pokemon){
        pokemonList.add(pokemon)
    }

    fun updatePokemonAdapter(){
        mainFragment.updateMoviesAdapter(pokemonList)
    }

    private inner class FetchTypesPokemon : AsyncTask<String, Unit, String>() {

        override fun doInBackground(vararg params: String): String {

            if (params.isNullOrEmpty()) return ""

            val pokemonType = params[0]
            val pokemonTypeURL = NetworkUtils().buildUrlByType(pokemonType)

            return try {
                NetworkUtils().getResponseFromHttpUrl(pokemonTypeURL)
            } catch (e: IOException) {
                ""
            }
        }

        override fun onPostExecute(typeInfo: String) {
            super.onPostExecute(typeInfo)
            if (!typeInfo.isEmpty()) {
                val root = JSONObject(typeInfo)
                val results = root.getJSONArray("pokemon")
                val cantidad:Int = results.length()
                var x = 0;
                while ( x != cantidad) {
                    val resulty = JSONObject(results[x].toString())
                    val result = JSONObject(resulty.getString("pokemon"))
                    FetchPokemon().execute(result.getString("name"))
                    x++
                }
            } else {
                Toast.makeText(this@MainActivity,"No se ha popido conectar con el API",Toast.LENGTH_LONG);
            }
        }
    }

    private inner class FetchPokemon: AsyncTask<String, Unit, String>() {
        override fun doInBackground(vararg params: String): String {
            if (params.isNullOrEmpty()) return ""

            val pokemonName = params[0]
            val pokemonURL = NetworkUtils().buildUrlByName(pokemonName)

            return try {
                NetworkUtils().getResponseFromHttpUrl(pokemonURL)
            } catch (e: IOException) {
                ""
            }
        }

        override fun onPostExecute(pokemonInfo: String) {
            super.onPostExecute(pokemonInfo)
            if(pokemonInfo.isNotEmpty()){
                val root = JSONObject(pokemonInfo)
                val pokemonId = root.getInt("id")
                val pokemonName = root.getString("name")

                var img = root.getJSONObject("sprites")
                var imgPokemon = img.getString("front_default")
                var typePokemon = root.getJSONArray("types")
                var type1 = JSONObject(typePokemon[0].toString())
                var type_1 = JSONObject(type1.getString("type"))
                var t1 = type_1.getString("name")
                if (typePokemon.length()>1) {
                    var type2 = JSONObject(typePokemon[1].toString())
                    var type_2 = JSONObject(type2.getString("type"))

                    var t2 = type_2.getString("name")
                    var poke = Pokemon(
                        pokemonId,pokemonName,t1,t2,root.getString("height"),root.getString("weight"),"url",imgPokemon
                    )
                    addPokemonToList(poke)
                    updatePokemonAdapter()
                }else {
                    var poke = Pokemon(
                        pokemonId,
                        pokemonName,
                        t1,
                        "",
                        root.getString("height"),
                        root.getString("weight"),
                        "url",
                        imgPokemon
                    )
                    addPokemonToList(poke)
                    updatePokemonAdapter()
                }

            }
        }
    }

}
