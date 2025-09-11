package com.example.manutencaodeveiculos

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PostosCombustivelActivity : AppCompatActivity() {

    private lateinit var viewModel: PostosViewModel
    private lateinit var rvPostos: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvErro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postos_combustivel)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(PostosViewModel::class.java)

        rvPostos = findViewById(R.id.rvPostos)
        progressBar = findViewById(R.id.progressBar)
        tvErro = findViewById(R.id.tvErro)

        rvPostos.layoutManager = LinearLayoutManager(this)

        setupObservers()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setupObservers() {
        viewModel.postos.observe(this) { postos ->
            if (postos.isNullOrEmpty()) {

            } else {
                rvPostos.adapter = PostosAdapter(postos)
                rvPostos.visibility = View.VISIBLE
                tvErro.visibility = View.GONE
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                tvErro.text = errorMessage
                tvErro.visibility = View.VISIBLE
                rvPostos.visibility = View.GONE
            } else {
                tvErro.visibility = View.GONE
            }
        }
    }
}