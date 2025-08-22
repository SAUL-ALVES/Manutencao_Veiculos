package com.example.manutenodeveculos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity

class CadastroVeiculoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.cadastro_veiculo)

        val btnSalvarVeiculo = findViewById<Button>(R.id.btnSalvarVeiculo)
        val spinnerTipo = findViewById<Spinner>(R.id.spinnerTipo)
        val tipos = resources.getStringArray(R.array.tipos_veiculo)


        val adapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            tipos
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                if (position == 0) {

                    view.setTextColor(Color.GRAY)
                } else {
                    view.setTextColor(Color.BLACK)
                }
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(Color.BLACK)
                return view
            }
        }


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipo.adapter = adapter
        spinnerTipo.setSelection(0)

        btnSalvarVeiculo.setOnClickListener {
            if (spinnerTipo.selectedItemPosition == 0) {
                Toast.makeText(this, "Selecione o tipo de veículo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

    }
}
