package com.example.manutencaodeveiculos.network

import com.example.manutencaodeveiculos.data.Posto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ApiService {
    @GET("postos")
    suspend fun getPostos(): Response<List<Posto>>
}


object RetrofitInstance {

    private const val BASE_URL = "https://my-json-server.typicode.com/SAUL-ALVES/api-precos-combustivel/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}