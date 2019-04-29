package com.example.pokedex.Network

import android.net.Uri
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class NetworkUtils {
    val BASEURL = "https://pokeapi.co/api/v2"

    fun buildUrlByType(type:String) : URL {
        val BASEURLBYTYPE = "$BASEURL/type"
        val builtUri = Uri.parse(BASEURLBYTYPE)
            .buildUpon()
            .appendPath(type)
            .build()
        return try {
            URL(builtUri.toString())
        }catch (e : MalformedURLException){
            URL("")
        }
    }

    fun buildUrlByName(name:String) : URL {
        val BASEURLBYTYPE = "$BASEURL/pokemon"
        val builtUri = Uri.parse(BASEURLBYTYPE)
            .buildUpon()
            .appendPath(name)
            .build()
        return try {
            URL(builtUri.toString())
        }catch (e : MalformedURLException){
            URL("")
        }
    }

    @Throws(IOException::class)
    fun getResponseFromHttpUrl(url: URL):String{
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val `in` = urlConnection.inputStream

            val scanner = Scanner(`in`)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            return if(hasInput){
                scanner.next()
            }else{
                ""
            }
        }finally {
            urlConnection.disconnect()
        }
    }
}