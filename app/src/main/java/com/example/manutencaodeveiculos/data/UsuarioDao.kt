package com.example.manutencaodeveiculos.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    /**
     * Insere um usuário no banco de dados.
     * A palavra-chave "suspend" garante que esta operação não trave a tela.
     * O retorno "Long" é o ID do novo usuário inserido.
     */
    @Insert
    suspend fun inserir(usuario: Usuario): Long

    /**
     * Busca o primeiro (e único) usuário da tabela e o expõe como LiveData,
     * permitindo que a UI reaja a mudanças nele.
     */
    @Query("SELECT * FROM usuarios LIMIT 1")
    fun getUsuarioLiveData(): LiveData<Usuario?>

    @Query("SELECT orcamentoMensal FROM usuarios WHERE id = :usuarioId")
    fun getOrcamento(usuarioId: Int): LiveData<Double?>
}