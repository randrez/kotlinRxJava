package com.example.firstappkotlin.models

import com.google.gson.annotations.SerializedName

class Ability(
    @SerializedName("ability")
    val ability:AbilityDetail,
    @SerializedName("is_hidden")
    val hidden:Boolean,
    @SerializedName("slot")
    val slot:Int) {

    override fun toString(): String {
        return "Ability(ability=$ability, hidden=$hidden, slot=$slot)"
    }
}