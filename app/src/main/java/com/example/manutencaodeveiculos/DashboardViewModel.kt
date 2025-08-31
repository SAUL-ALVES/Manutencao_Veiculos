package com.example.manutencaodeveiculos


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.example.manutencaodeveiculos.data.AppDatabase
import com.example.manutencaodeveiculos.data.Usuario
import com.example.manutencaodeveiculos.data.Veiculo

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val db: AppDatabase = AppDatabase.getDatabase(application)

    // LiveData para o usuário.
    val usuario: LiveData<Usuario?> = db.usuarioDao().getUsuarioLiveData()

    // LiveData para a lista de veículos.
    // Usa switchMap para reagir a mudanças no usuário. Se o usuário mudar, a lista de veículos é recarregada.
    val veiculos: LiveData<List<Veiculo>> = usuario.switchMap { usuarioAtual ->
        usuarioAtual?.let {
            db.veiculoDao().buscarVeiculosDoUsuario(it.id)
        }
    }

    // LiveData para o gasto total.
    val gastoTotal: LiveData<Double?> = usuario.switchMap { usuarioAtual ->
        usuarioAtual?.let {
            db.manutencaoDao().getGastoTotalDoUsuario(it.id)
        }
    }
}