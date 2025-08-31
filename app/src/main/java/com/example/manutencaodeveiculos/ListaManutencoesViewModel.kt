package com.example.manutencaodeveiculos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.manutencaodeveiculos.data.AppDatabase
import com.example.manutencaodeveiculos.data.Manutencao

class ListaManutencoesViewModel(application: Application) : AndroidViewModel(application) {

    private val manutencaoDao = AppDatabase.getDatabase(application).manutencaoDao()

    // LiveData que contém a lista de todas as manutenções
    // Para simplificar, estamos buscando de todos os veículos.
    // Se quisesse de um veículo específico, passaria o ID do veículo aqui.
    val todasManutencoes: LiveData<List<Manutencao>>

    init {
        // Inicializa a busca por todas as manutenções
        todasManutencoes = manutencaoDao.buscarTodasManutencoes()
    }
}