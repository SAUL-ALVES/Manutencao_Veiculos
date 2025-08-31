package com.example.manutencaodeveiculos.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ManutencaoDao {
    @Insert
    suspend fun inserir(manutencao: Manutencao)

    @Query("SELECT * FROM manutencoes WHERE veiculoId = :idVeiculo ORDER BY data DESC")
    fun buscarManutencoesDoVeiculo(idVeiculo: Int): LiveData<List<Manutencao>>

    @Query("""
        SELECT SUM(m.custo) FROM manutencoes m
        INNER JOIN veiculos v ON m.veiculoId = v.id
        WHERE v.usuarioId = :usuarioId
    """)
    fun getGastoTotalDoUsuario(usuarioId: Int): LiveData<Double?>

    @Query("SELECT * FROM manutencoes ORDER BY data DESC")
    fun buscarTodasManutencoes(): LiveData<List<Manutencao>>

    @Query("""
    SELECT categoria, SUM(custo) as total FROM manutencoes
    GROUP BY categoria
    HAVING SUM(custo) > 0
""")
    fun getGastosPorCategoria(): LiveData<List<GastoPorCategoria>>

    @Query("""
    SELECT SUBSTR(data, 7, 4) || '-' || SUBSTR(data, 4, 2) as anoMes, SUM(custo) as total
    FROM manutencoes
    GROUP BY anoMes
    ORDER BY anoMes ASC
""")
    fun getGastosMensais(): LiveData<List<GastoMensal>>
}