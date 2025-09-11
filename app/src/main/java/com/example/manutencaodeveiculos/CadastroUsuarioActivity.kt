package com.example.manutencaodeveiculos


import android.content.Context // --> IMPORT ADICIONADO
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.manutencaodeveiculos.data.AppDatabase
import com.example.manutencaodeveiculos.data.Usuario
import kotlinx.coroutines.launch

class CadastroUsuarioActivity : AppCompatActivity() {


    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_usuario)


        db = AppDatabase.getDatabase(this)

        val etNome = findViewById<EditText>(R.id.etNome)
        val etOrcamento = findViewById<EditText>(R.id.etOrcamento)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        btnSalvar.setOnClickListener {
            val nome = etNome.text.toString().trim()
            val orcamentoStr = etOrcamento.text.toString().trim()

            if (nome.isEmpty() || orcamentoStr.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val orcamento = orcamentoStr.toDoubleOrNull()
            if (orcamento == null) {
                Toast.makeText(this, "Valor de orçamento inválido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val novoUsuario = Usuario(nome = nome, orcamentoMensal = orcamento)


            lifecycleScope.launch {

                val novoUsuarioId = db.usuarioDao().inserir(novoUsuario)


                val sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                with(sharedPrefs.edit()) {
                    putBoolean("USER_REGISTERED", true)
                    putInt("CURRENT_USER_ID", novoUsuarioId.toInt())
                    apply()
                }

                Toast.makeText(this@CadastroUsuarioActivity, "Usuário salvo!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@CadastroUsuarioActivity, CadastroVeiculoActivity::class.java))
                finish()
            }
        }
    }
}