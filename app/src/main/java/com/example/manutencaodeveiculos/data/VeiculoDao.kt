package com.example.manutencaodeveiculos.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VeiculoDao {
    @Insert
    suspend fun inserir(veiculo: Veiculo)


    @Query("SELECT * FROM veiculos WHERE usuarioId = :idDoUsuario")
    fun buscarVeiculosDoUsuario(idDoUsuario: Int): LiveData<List<Veiculo>>
}