package com.example.manutencaodeveiculos.data

import com.google.gson.annotations.SerializedName

data class Posto(
    val id: Int,
    val nome: String,
    val endereco: String,
    val precos: Precos,
    @SerializedName("ultimaAtualizacao")
    val ultimaAtualizacao: String
)

data class Precos(
    val gasolinaComum: Double,
    val gasolinaAditivada: Double,
    val etanol: Double,
    val dieselS10: Double
)