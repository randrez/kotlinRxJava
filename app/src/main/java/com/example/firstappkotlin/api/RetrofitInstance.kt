package com.example.firstappkotlin.api

import com.example.firstappkotlin.utils.Constants.Companion.BASE_URL
import com.example.firstappkotlin.utils.Constants.Companion.BASE_URL2
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(InterceptorApi())
    }.build()



    private val retrofitLogin by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(GetData::class.java)
    }

    fun api():GetData{
        return retrofitLogin
    }

    private val clientOther = OkHttpClient
        .Builder()
        .build()

    private val retrofitOther by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientOther)
            .build()
            .create(GetData::class.java)
    }

    fun apiOther():GetData{
        return  retrofitOther
    }

}



