package com.example.firstappkotlin.api

import com.example.firstappkotlin.models.Login
import com.example.firstappkotlin.models.Pokemon
import com.example.firstappkotlin.models.ResponseData
import io.reactivex.Observable
import retrofit2.http.*


interface GetData {

    @GET(".")
    fun login(@Query("p") password:String):Observable<Login>

    @GET(".")
    fun getPokemons(@Query("limit") limit:Int):Observable<ResponseData>

    @GET
    fun getPokemon(@Url url: String?) : Observable<Pokemon>
}