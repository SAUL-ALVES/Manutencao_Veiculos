package com.example.manutencaodeveiculos

import android.content.Context // --> IMPORT NECESSÁRIO
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.manutencaodeveiculos.data.AppDatabase
import com.example.manutencaodeveiculos.data.Veiculo
import kotlinx.coroutines.launch

class CadastroVeiculoActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_veiculo)

        db = AppDatabase.getDatabase(this)

        val etNomeVeiculo = findViewById<EditText>(R.id.etNomeVeiculo)
        val etPlaca = findViewById<EditText>(R.id.etPlaca)
        val etQuilometragem = findViewById<EditText>(R.id.etQuilometragem)
        val spinnerTipo = findViewById<Spinner>(R.id.spinnerTipo)
        val btnSalvarVeiculo = findViewById<Button>(R.id.btnSalvarVeiculo)

        // Sua lógica de personalização do Spinner (está ótima!)
        val tipos = resources.getStringArray(R.array.tipos_veiculo)
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipos) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(Color.GRAY)
                } else {
                    view.setTextColor(Color.BLACK)
                }
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(Color.BLACK)
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipo.adapter = adapter
        spinnerTipo.setSelection(0)

        // Lógica de salvamento atualizada
        btnSalvarVeiculo.setOnClickListener {
            val nome = etNomeVeiculo.text.toString().trim()
            val placa = etPlaca.text.toString().trim()
            val kmStr = etQuilometragem.text.toString().trim()
            val tipo = spinnerTipo.selectedItem.toString()

            // Sua validação (está ótima!)
            if (spinnerTipo.selectedItemPosition == 0) {
                Toast.makeText(this, "Selecione o tipo de veículo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (nome.isEmpty() || placa.isEmpty() || kmStr.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val quilometragem = kmStr.toIntOrNull()
            if (quilometragem == null) {
                Toast.makeText(this, "Quilometragem inválida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Inicia Coroutine para salvar no banco de dados
            lifecycleScope.launch {
                // --> CORREÇÃO 1: Buscando o ID do SharedPreferences
                val sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                val usuarioIdAtual = sharedPrefs.getInt("CURRENT_USER_ID", -1)

                // Validação para garantir que o ID do usuário foi encontrado
                if (usuarioIdAtual == -1) {
                    Toast.makeText(this@CadastroVeiculoActivity, "Erro: Sessão de usuário inválida. Tente novamente.", Toast.LENGTH_LONG).show()
                    return@launch
                }

                // --> CORREÇÃO 2: Criando o objeto Veiculo com os campos corretos
                val novoVeiculo = Veiculo(
                    usuarioId = usuarioIdAtual, // Usa o ID salvo na sessão
                    nome = nome,
                    placa = placa,
                    quilometragem = quilometragem,
                    tipo = tipo
                )
                db.veiculoDao().inserir(novoVeiculo)

                Toast.makeText(this@CadastroVeiculoActivity, "Veículo salvo com sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@CadastroVeiculoActivity, DashboardActivity::class.java))
                finishAffinity() // Ótima escolha para fechar as telas de cadastro
            }
        }
    }
}