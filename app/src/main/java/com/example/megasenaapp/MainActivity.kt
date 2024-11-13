package com.example.megasenaapp

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.megasenaapp.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedpref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedpref = getSharedPreferences("Prefs", MODE_PRIVATE)
        val result = sharedpref.getString("result", null)
        if (result != null) {
            binding.textView2.text = "Última aposta: $result"
        }

        binding.ButtonGerar.setOnClickListener {
            gerarNumeros(binding.editTextText.text.toString(), binding.textView2)
        }

    }

    private fun gerarNumeros(text: String, txtResult: TextView) {
        sharedpref = getSharedPreferences("Prefs", MODE_PRIVATE)
        if (text.isEmpty() || text.toInt() < 6 || text.toInt() > 15) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }
        val qtd = text.toInt()
        val numeros = mutableSetOf<Int>()

        while(true) {
            val numero = Random.nextInt(60)
            numeros.add(numero+1)
            if (numeros.size == qtd) {
                break
            }
        }
        txtResult.text = numeros.joinToString(" - ")
        sharedpref.edit().putString("result", txtResult.text.toString()).apply()
    }
}