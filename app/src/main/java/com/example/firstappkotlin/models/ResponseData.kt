package com.example.firstappkotlin.models

import com.google.gson.annotations.SerializedName

class ResponseData(

    @SerializedName("count")
    var count:Int?,

    @SerializedName("next")
    var next:String?,

    @SerializedName("previous")
    var previous:String?,

    @SerializedName("results")
    var listPokemon:List<ItemPokemon>) {

    override fun toString(): String {
        return "ResponseData(count=$count, next=$next, previous=$previous, results=${listPokemon.toString()})"
    }
}