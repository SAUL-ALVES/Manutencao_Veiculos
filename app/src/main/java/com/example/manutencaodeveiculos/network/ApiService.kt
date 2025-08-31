package com.example.manutencaodeveiculos.network

import com.example.manutencaodeveiculos.data.Posto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// 1. Interface que define os endpoints da API
interface ApiService {
    @GET("postos") // O caminho do endpoint que criamos
    suspend fun getPostos(): Response<List<Posto>>
}

// 2. Objeto Singleton para criar e gerenciar a instância do Retrofit
object RetrofitInstance {
    // Substitua pelo seu usuário e nome do repositório!
    private const val BASE_URL = "https://my-json-server.typicode.com/SAUL-ALVES/api-precos-combustivel/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}