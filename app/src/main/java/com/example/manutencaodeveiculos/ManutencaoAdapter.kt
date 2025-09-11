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


    class ManutencaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tipo: TextView = itemView.findViewById(R.id.tvTipoManutencao)
        val data: TextView = itemView.findViewById(R.id.tvDataManutencao)
        val custo: TextView = itemView.findViewById(R.id.tvCustoManutencao)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManutencaoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_manutencao, parent, false)
        return ManutencaoViewHolder(view)
    }


    override fun onBindViewHolder(holder: ManutencaoViewHolder, position: Int) {
        val manutencao = manutenções[position]
        holder.tipo.text = manutencao.tipo
        holder.data.text = "Data: ${manutencao.data}"


        val formatadorMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        holder.custo.text = "Custo: ${formatadorMoeda.format(manutencao.custo)}"
    }


    override fun getItemCount() = manutenções.size


    fun updateData(newManutencoes: List<Manutencao>) {
        this.manutenções = newManutencoes
        notifyDataSetChanged()
    }
}