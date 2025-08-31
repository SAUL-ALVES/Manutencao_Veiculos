package com.example.manutencaodeveiculos.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "veiculos",
    // Define que 'usuarioId' é uma chave estrangeira que aponta para o 'id' da tabela 'usuarios'
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [Index("usuarioId")]
)
data class Veiculo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuarioId: Int, // Chave para ligar ao Usuário
    val nome: String,
    val placa: String,
    val quilometragem: Int,
    val tipo: String



)