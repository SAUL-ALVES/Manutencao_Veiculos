package com.example.manutencaodeveiculos.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {

    @Insert
    suspend fun inserir(usuario: Usuario): Long


    @Query("SELECT * FROM usuarios LIMIT 1")
    fun getUsuarioLiveData(): LiveData<Usuario?>

    @Query("SELECT orcamentoMensal FROM usuarios WHERE id = :usuarioId")
    fun getOrcamento(usuarioId: Int): LiveData<Double?>
}