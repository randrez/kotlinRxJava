package com.example.firstappkotlin.models

import com.google.gson.annotations.SerializedName

class ItemPokemon(

    @SerializedName("name")
    val name: String?,

    @SerializedName("url")
    val url: String?
) {

    override fun toString(): String {
        return "ItemPokemon(name=$name, url=$url)"
    }
}