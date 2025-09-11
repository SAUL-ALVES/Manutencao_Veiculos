package com.example.manutencaodeveiculos

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.NumberFormat
import java.util.Locale

class GraficosRelatoriosActivity : AppCompatActivity() {

    private lateinit var viewModel: GraficosViewModel
    private lateinit var pieChart: PieChart
    private lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.graficos_relatorios)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(GraficosViewModel::class.java)
        pieChart = findViewById(R.id.pieChartCategorias)
        barChart = findViewById(R.id.barChartGastosMensais)

        setupObservers()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setupObservers() {
        viewModel.gastosPorCategoria.observe(this) { gastos ->
            if (gastos.isNullOrEmpty()) {
                pieChart.setNoDataText("Sem dados de gastos por categoria.")
                pieChart.invalidate()
            } else {
                setupPieChart(gastos.map { PieEntry(it.total, it.categoria) })
            }
        }

        viewModel.gastosMensais.observe(this) { gastos ->
            viewModel.orcamentoUsuario.observe(this) { orcamento ->
                if (gastos.isNullOrEmpty()) {
                    barChart.setNoDataText("Sem dados de gastos mensais.")
                    barChart.invalidate()
                } else {
                    setupBarChart(gastos, orcamento ?: 0.0)
                }
            }
        }
    }

    private fun setupPieChart(entries: List<PieEntry>) {
        val dataSet = PieDataSet(entries, "Gastos por Categoria")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 12f
        dataSet.valueFormatter = PercentFormatter(pieChart)

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.animateY(1400)
        pieChart.invalidate()
    }

    private fun setupBarChart(gastos: List<com.example.manutencaodeveiculos.data.GastoMensal>, orcamento: Double) {
        val gastoEntries = gastos.mapIndexed { index, gasto -> BarEntry(index.toFloat(), gasto.total) }
        val orcamentoEntries = gastos.mapIndexed { index, _ -> BarEntry(index.toFloat(), orcamento.toFloat()) }

        val gastoDataSet = BarDataSet(gastoEntries, "Gasto Real")
        gastoDataSet.color = ContextCompat.getColor(this, R.color.corGasto)

        val orcamentoDataSet = BarDataSet(orcamentoEntries, "Orçamento")
        orcamentoDataSet.color = ContextCompat.getColor(this, R.color.corOrcamento)

        val data = BarData(gastoDataSet, orcamentoDataSet)
        barChart.data = data

        val meses = gastos.map { it.anoMes.substring(5, 7) + "/" + it.anoMes.substring(2, 4) }
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(meses)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.granularity = 1f
        barChart.xAxis.setDrawGridLines(false)

        barChart.description.isEnabled = false
        barChart.animateY(1000)

        val groupSpace = 0.4f
        val barSpace = 0.05f
        val barWidth = 0.25f
        data.barWidth = barWidth
        barChart.groupBars(0f, groupSpace, barSpace)

        barChart.invalidate()
    }
}