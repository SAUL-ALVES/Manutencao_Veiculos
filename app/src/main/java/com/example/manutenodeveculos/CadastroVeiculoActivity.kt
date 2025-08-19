package com.example.manutenodeveculos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class CadastroVeiculoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.cadastro_veiculo)

        val btnSalvarVeiculo = findViewById<Button>(R.id.btnSalvarVeiculo)


        btnSalvarVeiculo.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

    }
}