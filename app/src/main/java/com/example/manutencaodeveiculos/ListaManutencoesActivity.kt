package com.example.manutencaodeveiculos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaManutencoesActivity : AppCompatActivity() {

    private lateinit var viewModel: ListaManutencoesViewModel
    private lateinit var manutencaoAdapter: ManutencaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_manutencoes)

        // 1. Inicializa o ViewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ListaManutencoesViewModel::class.java)

        // 2. Configura o RecyclerView
        val rvManutencoes = findViewById<RecyclerView>(R.id.rvManutencoes)
        manutencaoAdapter = ManutencaoAdapter(emptyList()) // Começa com uma lista vazia
        rvManutencoes.layoutManager = LinearLayoutManager(this)
        rvManutencoes.adapter = manutencaoAdapter

        // 3. Observa as mudanças nos dados
        viewModel.todasManutencoes.observe(this) { manutenções ->
            // Quando a lista de manutenções mudar no banco de dados,
            // esta função será chamada automaticamente.
            manutenções?.let {
                // Atualiza o adapter com os novos dados.
                manutencaoAdapter.updateData(it)
            }
        }
    }
}