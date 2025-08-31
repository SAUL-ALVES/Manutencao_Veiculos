package com.example.manutencaodeveiculos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manutencaodeveiculos.data.Manutencao
import java.text.NumberFormat
import java.util.Locale

class ManutencaoAdapter(private var manutenções: List<Manutencao>) :
    RecyclerView.Adapter<ManutencaoAdapter.ManutencaoViewHolder>() {

    // ViewHolder: Gerencia as views de cada item da lista
    class ManutencaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tipo: TextView = itemView.findViewById(R.id.tvTipoManutencao)
        val data: TextView = itemView.findViewById(R.id.tvDataManutencao)
        val custo: TextView = itemView.findViewById(R.id.tvCustoManutencao)
    }

    // Cria uma nova view (invocado pelo layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManutencaoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_manutencao, parent, false)
        return ManutencaoViewHolder(view)
    }

    // Vincula os dados à view (invocado pelo layout manager)
    override fun onBindViewHolder(holder: ManutencaoViewHolder, position: Int) {
        val manutencao = manutenções[position]
        holder.tipo.text = manutencao.tipo
        holder.data.text = "Data: ${manutencao.data}"

        // Formata o custo como moeda
        val formatadorMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        holder.custo.text = "Custo: ${formatadorMoeda.format(manutencao.custo)}"
    }

    // Retorna o tamanho da lista (invocado pelo layout manager)
    override fun getItemCount() = manutenções.size

    // Função para atualizar a lista de dados e notificar o adapter
    fun updateData(newManutencoes: List<Manutencao>) {
        this.manutenções = newManutencoes
        notifyDataSetChanged() // Notifica o RecyclerView para redesenhar a lista
    }
}