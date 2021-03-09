package com.example.firstappkotlin.models

import com.google.gson.annotations.SerializedName


class Login(

    @SerializedName("api_status")
    var apiStatus: String?,

    @SerializedName("message")
    var message: String?,

    var email: String?
) {

    override fun toString(): String {
        return "{apiStatus:$apiStatus, message:$message, email:$email}"
    }
}