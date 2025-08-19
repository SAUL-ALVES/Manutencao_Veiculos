package com.example.manutenodeveculos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class CadastroUsuarioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.cadastro_usuario)

        val etNome = findViewById<EditText>(R.id.etNome)
        val etOrcamento = findViewById<EditText>(R.id.etOrcamento)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)


        btnSalvar.setOnClickListener {
            startActivity(Intent(this, CadastroVeiculoActivity::class.java ))
        }


    }
}