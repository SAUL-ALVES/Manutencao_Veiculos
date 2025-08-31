package com.example.manutencaodeveiculos.data

// Armazena o resultado de: "Tipo de Manutenção" e "Soma dos Custos"
data class GastoPorCategoria(
    val categoria: String,
    val total: Float // O gráfico usa Float, então já convertemos aqui
)

// Armazena o resultado de: "Ano-Mês" e "Soma dos Custos"
data class GastoMensal(
    val anoMes: String, // Formato "YYYY-MM"
    val total: Float
)