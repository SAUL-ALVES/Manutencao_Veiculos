package com.example.manutencaodeveiculos


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manutencaodeveiculos.data.Veiculo



class VeiculoAdapter(private var veiculos: List<Veiculo>) :
    RecyclerView.Adapter<VeiculoAdapter.VeiculoViewHolder>() {

    class VeiculoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.tvNomeVeiculo)
        val placa: TextView = itemView.findViewById(R.id.tvPlacaVeiculo)
        val km: TextView = itemView.findViewById(R.id.tvKmVeiculo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VeiculoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_veiculo, parent, false)
        return VeiculoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VeiculoViewHolder, position: Int) {
        val veiculo = veiculos[position]
        holder.nome.text = veiculo.nome
        holder.placa.text = veiculo.placa
        holder.km.text = "${veiculo.quilometragem} km"
    }

    override fun getItemCount() = veiculos.size

    fun updateData(newVeiculos: List<Veiculo>) {
        veiculos = newVeiculos
        notifyDataSetChanged()
    }
}