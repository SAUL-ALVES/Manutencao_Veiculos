package com.example.manutencaodeveiculos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.manutencaodeveiculos.data.AppDatabase
import com.example.manutencaodeveiculos.data.GastoMensal
import com.example.manutencaodeveiculos.data.GastoPorCategoria

class GraficosViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val manutencaoDao = db.manutencaoDao()
    private val usuarioDao = db.usuarioDao()

    val gastosPorCategoria: LiveData<List<GastoPorCategoria>>
    val gastosMensais: LiveData<List<GastoMensal>>
    val orcamentoUsuario: LiveData<Double?>

    init {
        gastosPorCategoria = manutencaoDao.getGastosPorCategoria()
        gastosMensais = manutencaoDao.getGastosMensais()


        orcamentoUsuario = usuarioDao.getOrcamento(1)
    }
}