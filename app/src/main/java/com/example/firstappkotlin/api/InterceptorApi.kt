package com.example.firstappkotlin.api


import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class  InterceptorApi:Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
            .newBuilder()
            .addHeader("content-type", "application/json")
            .addHeader("x-rapidapi-key", "59bb313dc8mshf4509b895611cd8p1b3b33jsn3624abcfb633")
            .addHeader("x-rapidapi-host", "ispasswordpwned.p.rapidapi.com")
            .build()

        return chain.proceed(request)
    }

}