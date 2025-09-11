package com.example.manutencaodeveiculos

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.text.NumberFormat
import java.util.Locale

class DashboardActivity : AppCompatActivity() {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var veiculoAdapter: VeiculoAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { item ->
            drawerLayout.closeDrawers()
            when (item.itemId) {
                R.id.action_show_historico -> {
                    startActivity(Intent(this, ListaManutencoesActivity::class.java))
                    true
                }
                R.id.action_show_graficos -> {
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




        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(DashboardViewModel::class.java)

        val rvVeiculos = findViewById<RecyclerView>(R.id.rvVeiculos)
        veiculoAdapter = VeiculoAdapter(emptyList())
        rvVeiculos.layoutManager = LinearLayoutManager(this)
        rvVeiculos.adapter = veiculoAdapter


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


        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        fabAdd.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.fab_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_add_veiculo -> {
                        startActivity(Intent(this, CadastroVeiculoActivity::class.java))
                        true
                    }
                    R.id.action_add_manutencao -> {
                        startActivity(Intent(this, RegistroManutencaoActivity::class.java))
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun atualizarTextoOrcamento(textView: TextView, orcamento: Double?, gasto: Double?) {
        val formatadorMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        val orcamentoFormatado = orcamento?.let { formatadorMoeda.format(it) } ?: "R$ 0,00"
        val gastoFormatado = gasto?.let { formatadorMoeda.format(it) } ?: "R$ 0,00"
        textView.text = "Orçamento: $orcamentoFormatado   |   Gasto: $gastoFormatado"
    }
}