package com.example.manutencaodeveiculos.data


data class GastoPorCategoria(
    val categoria: String,
    val total: Float
)


data class GastoMensal(
    val anoMes: String,
    val total: Float
)