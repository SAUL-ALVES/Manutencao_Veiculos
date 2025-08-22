package com.example.manutenodeveculos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dashboard)

        val btnHistorico = findViewById<Button>(R.id.btnHistorico)
        val btnManutencao = findViewById<Button>(R.id.btnRegistrarManutencao)
        val btnGraficos = findViewById<Button>(R.id.btnGraficos)

        btnManutencao.setOnClickListener {
            startActivity(Intent(this, RegistroManutencaoActivity::class.java))
        }

        btnHistorico.setOnClickListener {
            startActivity(Intent(this, ListaManutencoesActivity::class.java))

        }

        btnGraficos.setOnClickListener {
            val intent = Intent(this, GraficosRelatoriosActivity::class.java)
            startActivity(intent)
        }

    }
}