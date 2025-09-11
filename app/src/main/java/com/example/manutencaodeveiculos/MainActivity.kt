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


        val sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)


        val isUserRegistered = sharedPrefs.getBoolean("USER_REGISTERED", false)


        val intent = if (isUserRegistered) {

            Intent(this, DashboardActivity::class.java)
        } else {

            Intent(this, CadastroUsuarioActivity::class.java)
        }


        startActivity(intent)


        finish()
    }
}