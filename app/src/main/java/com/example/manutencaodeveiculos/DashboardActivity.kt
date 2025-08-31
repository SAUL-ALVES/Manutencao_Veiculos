package com.example.manutencaodeveiculos

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton // Importe esta classe
import java.text.NumberFormat
import java.util.Locale

class DashboardActivity : AppCompatActivity() {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var veiculoAdapter: VeiculoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        // --- PARTE 1: INICIALIZAÇÃO E CONFIGURAÇÃO DOS DADOS ---
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(DashboardViewModel::class.java)

        val rvVeiculos = findViewById<RecyclerView>(R.id.rvVeiculos)
        veiculoAdapter = VeiculoAdapter(emptyList())
        rvVeiculos.layoutManager = LinearLayoutManager(this)
        rvVeiculos.adapter = veiculoAdapter

        // --- PARTE 2: OBSERVAÇÃO DOS DADOS (ATUALIZAÇÃO DA TELA) ---
        val tvOrcamento = findViewById<TextView>(R.id.tvOrcamento)

        viewModel.usuario.observe(this) { usuario ->
            usuario?.let {
                atualizarTextoOrcamento(tvOrcamento, it.orcamentoMensal, viewModel.gastoTotal.value)
            }
        }
        viewModel.gastoTotal.observe(this) { gasto ->
            atualizarTextoOrcamento(tvOrcamento, viewModel.usuario.value?.orcamentoMensal, gasto)
        }

        viewModel.veiculos.observe(this) { veiculos ->
            veiculoAdapter.updateData(veiculos)
        }

        // --- PARTE 3: LÓGICA DO NOVO BOTÃO FLUTUANTE (FAB) ---
        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        fabAdd.setOnClickListener { view ->
            // Cria um PopupMenu ancorado no botão FAB
            val popupMenu = PopupMenu(this, view)
            // Infla o menu que criamos no arquivo fab_menu.xml
            popupMenu.menuInflater.inflate(R.menu.fab_menu, popupMenu.menu)

            // Define o que acontece quando um item do menu é clicado
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_add_veiculo -> {
                        // Navega para a tela de cadastro de veículo
                        startActivity(Intent(this, CadastroVeiculoActivity::class.java))
                        true
                    }
                    R.id.action_add_manutencao -> {
                        // Navega para a tela de registro de manutenção
                        startActivity(Intent(this, RegistroManutencaoActivity::class.java))
                        true
                    }
                    R.id.action_show_historico -> {
                        // Navega para a tela de histórico
                        startActivity(Intent(this, ListaManutencoesActivity::class.java))
                        true
                    }
                    R.id.action_show_graficos -> {
                        // Navega para a tela de gráficos
                        startActivity(Intent(this, GraficosRelatoriosActivity::class.java))
                        true
                    }

                    R.id.action_ver_postos -> {
                        startActivity(Intent(this, PostosCombustivelActivity::class.java))
                        true
                    }

                    else -> false
                }
            }
            // Exibe o menu
            popupMenu.show()
        }
    }

    private fun atualizarTextoOrcamento(textView: TextView, orcamento: Double?, gasto: Double?) {
        val formatadorMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        val orcamentoFormatado = orcamento?.let { formatadorMoeda.format(it) } ?: "R$ 0,00"
        val gastoFormatado = gasto?.let { formatadorMoeda.format(it) } ?: "R$ 0,00"
        textView.text = "Orçamento: $orcamentoFormatado   |   Gasto: $gastoFormatado"
    }
}