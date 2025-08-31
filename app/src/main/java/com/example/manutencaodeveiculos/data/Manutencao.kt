package com.example.manutencaodeveiculos.data


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "manutencoes",
    foreignKeys = [ForeignKey(
        entity = Veiculo::class,
        parentColumns = ["id"],
        childColumns = ["veiculoId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Manutencao(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val veiculoId: Int,
    val categoria: String,
    val tipo: String,
    val data: String,
    val quilometragem: Int,
    val custo: Double
)