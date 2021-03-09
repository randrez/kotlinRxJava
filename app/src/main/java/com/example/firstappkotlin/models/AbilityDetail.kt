package com.example.firstappkotlin.models

import com.google.gson.annotations.SerializedName

class AbilityDetail(
    @SerializedName("name")
    val name:String) {

    override fun toString(): String {
        return "AbilityDetail(name='$name')"
    }
}