package com.example.manutenodeveculos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dashboard)

        val btnHistorico = findViewById<Button>(R.id.btnHistorico)

        btnHistorico.setOnClickListener {
            startActivity(Intent(this, ListaManutencoesActivity::class.java))

        }

    }
}