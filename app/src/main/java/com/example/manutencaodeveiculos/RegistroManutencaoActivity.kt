package com.example.manutencaodeveiculos

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.manutencaodeveiculos.data.AppDatabase
import com.example.manutencaodeveiculos.data.Manutencao
import com.example.manutencaodeveiculos.data.Veiculo
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegistroManutencaoActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private var listaDeVeiculosDoUsuario: List<Veiculo> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_manutencao)

        db = AppDatabase.getDatabase(this)

        // --- REFERÊNCIAS AOS COMPONENTES ---
        val autoCompleteVeiculo = findViewById<AutoCompleteTextView>(R.id.autoCompleteVeiculo)
        // 1. CORRIGIDO: Pegando o AutoCompleteTextView da categoria, não um Spinner.
        val autoCompleteCategoria = findViewById<AutoCompleteTextView>(R.id.autoCompleteCategoria)
        val etTipoManutencao = findViewById<TextInputEditText>(R.id.etTipoManutencao) // Este agora é a descrição
        val etData = findViewById<TextInputEditText>(R.id.etData)
        val etQuilometragem = findViewById<TextInputEditText>(R.id.etQuilometragem)
        val etCusto = findViewById<TextInputEditText>(R.id.etCusto)
        val btnSalvarManutencao = findViewById<Button>(R.id.btnSalvarManutencao)

        // --- CONFIGURAÇÃO DOS COMPONENTES ---
        carregarVeiculosDoUsuario(autoCompleteVeiculo)

        // 2. ADICIONADO: Lógica para popular o seletor de categorias
        popularSeletorDeCategorias(autoCompleteCategoria)

        etData.setOnClickListener {
            mostrarSeletorDeData(etData)
        }

        btnSalvarManutencao.setOnClickListener {
            // --- COLETA DE DADOS DA TELA ---
            val nomeVeiculoSelecionado = autoCompleteVeiculo.text.toString().trim()
            // 3. ADICIONADO: Pegando o valor da categoria selecionada
            val categoriaSelecionada = autoCompleteCategoria.text.toString().trim()
            val tipoManutencao = etTipoManutencao.text.toString().trim() // Descrição
            val data = etData.text.toString().trim()
            val kmStr = etQuilometragem.text.toString().trim()
            val custoStr = etCusto.text.toString().trim()

            // 4. ATUALIZADO: Validação agora inclui o campo de categoria
            if (nomeVeiculoSelecionado.isEmpty() || categoriaSelecionada.isEmpty() || tipoManutencao.isEmpty() || data.isEmpty() || kmStr.isEmpty() || custoStr.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val veiculoSelecionado = listaDeVeiculosDoUsuario.find { (it.nome + " (" + it.placa + ")") == nomeVeiculoSelecionado }
            if (veiculoSelecionado == null) {
                Toast.makeText(this, "Veículo selecionado é inválido!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val quilometragem = kmStr.toIntOrNull()
            val custo = custoStr.toDoubleOrNull()
            if (quilometragem == null || custo == null) {
                Toast.makeText(this, "Quilometragem ou custo inválido!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 5. ATUALIZADO: Criando o objeto Manutencao com o novo campo 'categoria'
            val novaManutencao = Manutencao(
                veiculoId = veiculoSelecionado.id,
                categoria = categoriaSelecionada, // Campo novo
                tipo = tipoManutencao,            // Campo que agora é a descrição
                data = data,
                quilometragem = quilometragem,
                custo = custo
            )

            lifecycleScope.launch {
                db.manutencaoDao().inserir(novaManutencao)
                Toast.makeText(this@RegistroManutencaoActivity, "Manutenção salva com sucesso!", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    // NOVA FUNÇÃO para popular as categorias
    private fun popularSeletorDeCategorias(autoCompleteCategoria: AutoCompleteTextView) {
        val categorias = listOf(
            "Revisão Periódica", "Freios", "Pneus", "Suspensão e Direção",
            "Motor", "Elétrica", "Documentação", "Estética e Limpeza",
            "Combustível", "Outros"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categorias)
        autoCompleteCategoria.setAdapter(adapter)
    }

    private fun carregarVeiculosDoUsuario(autoCompleteVeiculo: AutoCompleteTextView) {
        // ... seu código para carregar veículos permanece igual ...
        val sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        var usuarioIdAtual: Int
        try {
            usuarioIdAtual = sharedPrefs.getInt("CURRENT_USER_ID", -1)
        } catch (e: ClassCastException) {
            usuarioIdAtual = sharedPrefs.getLong("CURRENT_USER_ID", -1L).toInt()
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line)
        autoCompleteVeiculo.setAdapter(adapter)
        db.veiculoDao().buscarVeiculosDoUsuario(usuarioIdAtual).observe(this) { veiculos ->
            listaDeVeiculosDoUsuario = veiculos
            val nomesFormatados = veiculos.map { it.nome + " (" + it.placa + ")" }
            adapter.clear()
            adapter.addAll(nomesFormatados)
            adapter.notifyDataSetChanged()
        }
    }

    private fun mostrarSeletorDeData(editText: TextInputEditText) {
        // ... seu código do seletor de data permanece igual ...
        val calendario = Calendar.getInstance()
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
            val calendarioSelecionado = Calendar.getInstance().apply { set(year, monthOfYear, dayOfMonth) }
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            editText.setText(formato.format(calendarioSelecionado.time))
        }, ano, mes, dia).show()
    }
}