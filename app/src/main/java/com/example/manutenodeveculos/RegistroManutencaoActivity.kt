package com.example.manutenodeveculos

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// É uma boa prática usar AppCompatActivity para ter compatibilidade com recursos mais antigos
class RegistroManutencaoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_manutencao)

        // 1. Pegar as referências dos componentes do layout XML
        val spinnerVeiculo = findViewById<Spinner>(R.id.spinnerVeiculo)
        val etTipoManutencao = findViewById<EditText>(R.id.etTipoManutencao)
        val etData = findViewById<EditText>(R.id.etData)
        val etQuilometragem = findViewById<EditText>(R.id.etQuilometragem)
        val etCusto = findViewById<EditText>(R.id.etCusto)
        val btnSalvarManutencao = findViewById<Button>(R.id.btnSalvarManutencao)

        // 2. Configurar o Spinner de Veículos (com dados de exemplo)
        // No futuro, você vai buscar essa lista do banco de dados
        val veiculosExemplo = listOf("Fiat Uno", "Honda Biz")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, veiculosExemplo)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerVeiculo.adapter = adapter

        // 3. Configurar o seletor de data (DatePicker) para o campo de data
        etData.setOnClickListener {
            mostrarSeletorDeData(etData)
        }

        // 4. Configurar a ação do botão Salvar
        btnSalvarManutencao.setOnClickListener {
            // Coleta os dados dos campos
            val veiculoSelecionado = spinnerVeiculo.selectedItem.toString()
            val tipoManutencao = etTipoManutencao.text.toString().trim()
            val data = etData.text.toString().trim()
            val quilometragem = etQuilometragem.text.toString().trim()
            val custo = etCusto.text.toString().trim()

            // Validação simples
            if (tipoManutencao.isEmpty() || data.isEmpty() || quilometragem.isEmpty() || custo.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else {
                // Por enquanto, apenas exibimos os dados em um Toast
                val mensagem = "Manutenção salva para $veiculoSelecionado: $tipoManutencao"
                Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()

                // Futuramente, aqui você salvará os dados no banco de dados Room
                // e depois fechará a tela com finish()
                finish() // Fecha a tela de registro e volta para a anterior
            }
        }
    }

    private fun mostrarSeletorDeData(editText: EditText) {
        val calendario = Calendar.getInstance()
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val calendarioSelecionado = Calendar.getInstance()
                calendarioSelecionado.set(year, monthOfYear, dayOfMonth)

                // Formata a data para o padrão brasileiro (dd/MM/yyyy)
                val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dataFormatada = formato.format(calendarioSelecionado.time)
                editText.setText(dataFormatada)
            },
            ano,
            mes,
            dia
        )
        datePickerDialog.show()
    }
}
