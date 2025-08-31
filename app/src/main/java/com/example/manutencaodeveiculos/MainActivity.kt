package com.example.manutencaodeveiculos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.manutencaodeveiculos.CadastroUsuarioActivity
import com.example.manutencaodeveiculos.DashboardActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Acessa o nosso arquivo de preferências
        val sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // 2. Verifica se a nossa flag "USER_REGISTERED" é verdadeira. Se não encontrar, o padrão é 'false'.
        val isUserRegistered = sharedPrefs.getBoolean("USER_REGISTERED", false)

        // 3. Decide para qual tela o usuário deve ir
        val intent = if (isUserRegistered) {
            // Se o usuário já está cadastrado, vai para o Dashboard
            Intent(this, DashboardActivity::class.java)
        } else {
            // Se não, vai para a tela de Cadastro
            Intent(this, CadastroUsuarioActivity::class.java)
        }


        startActivity(intent)


        finish()
    }
}