package com.example.firstappkotlin.models

import com.google.gson.annotations.SerializedName

class Move(
    @SerializedName("move")
    val move:MoveDetail) {

    override fun toString(): String {
        return "Move(move=$move)"
    }
}