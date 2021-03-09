package com.example.firstappkotlin.models

import com.google.gson.annotations.SerializedName


class Pokemon(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("abilities")
    val abilities: List<Ability>,

    @SerializedName("moves")
    val moves: List<Move>
) {

    override fun toString(): String {
        return "Pokemon(id=$id, name='$name', abilities=$abilities, moves=$moves)"
    }
}