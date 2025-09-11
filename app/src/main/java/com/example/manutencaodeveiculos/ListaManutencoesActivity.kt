package com.example.manutencaodeveiculos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaManutencoesActivity : AppCompatActivity() {

    private lateinit var viewModel: ListaManutencoesViewModel
    private lateinit var manutencaoAdapter: ManutencaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_manutencoes)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ListaManutencoesViewModel::class.java)


        val rvManutencoes = findViewById<RecyclerView>(R.id.rvManutencoes)
        manutencaoAdapter = ManutencaoAdapter(emptyList())
        rvManutencoes.layoutManager = LinearLayoutManager(this)
        rvManutencoes.adapter = manutencaoAdapter


        viewModel.todasManutencoes.observe(this) { manutenções ->
            manutenções?.let {
                manutencaoAdapter.updateData(it)
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}