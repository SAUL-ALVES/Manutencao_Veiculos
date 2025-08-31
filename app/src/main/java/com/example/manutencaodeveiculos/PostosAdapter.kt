package com.example.manutencaodeveiculos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manutencaodeveiculos.data.Posto
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class PostosAdapter(private val postos: List<Posto>) : RecyclerView.Adapter<PostosAdapter.PostoViewHolder>() {

    class PostoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomePosto: TextView = itemView.findViewById(R.id.tvNomePosto)
        val enderecoPosto: TextView = itemView.findViewById(R.id.tvEnderecoPosto)
        val precoGasolinaComum: TextView = itemView.findViewById(R.id.tvPrecoGasolinaComum)
        val precoGasolinaAditivada: TextView = itemView.findViewById(R.id.tvPrecoGasolinaAditivada)
        val precoEtanol: TextView = itemView.findViewById(R.id.tvPrecoEtanol)
        val precoDieselS10: TextView = itemView.findViewById(R.id.tvPrecoDieselS10)
        val ultimaAtualizacao: TextView = itemView.findViewById(R.id.tvUltimaAtualizacao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_posto_combustivel, parent, false)
        return PostoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostoViewHolder, position: Int) {
        val posto = postos[position]
        val formatadorMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        holder.nomePosto.text = posto.nome
        holder.enderecoPosto.text = posto.endereco
        holder.precoGasolinaComum.text = formatadorMoeda.format(posto.precos.gasolinaComum)
        holder.precoGasolinaAditivada.text = formatadorMoeda.format(posto.precos.gasolinaAditivada)
        holder.precoEtanol.text = formatadorMoeda.format(posto.precos.etanol)
        holder.precoDieselS10.text = formatadorMoeda.format(posto.precos.dieselS10)
        holder.ultimaAtualizacao.text = "Atualizado em: ${formatarData(posto.ultimaAtualizacao)}"
    }

    override fun getItemCount() = postos.size

    private fun formatarData(dataString: String): String {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            parser.timeZone = TimeZone.getTimeZone("UTC")
            val data = parser.parse(dataString)
            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            data?.let { formatter.format(it) } ?: "Data indisponível"
        } catch (e: Exception) {
            "Data indisponível"
        }
    }
}