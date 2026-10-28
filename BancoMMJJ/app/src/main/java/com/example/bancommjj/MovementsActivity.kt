package com.example.bancommjj

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bancommjj.bd.MiBancoOperacional
import com.example.bancommjj.databinding.ActivityMovementsBinding
import com.example.bancommjj.pojo.Cliente
import com.example.bancommjj.pojo.Cuenta
import com.example.bancommjj.pojo.Movimiento

class MovementsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovementsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cliente = IntentCompat.getSerializableExtra(intent, "Cliente", Cliente::class.java)
            ?: run { finish(); return }

        val mbo = MiBancoOperacional.getInstance(this)
        val cuentas = (mbo?.getCuentas(cliente) ?: arrayListOf<Any>()).filterIsInstance<Cuenta>()
        val textos = cuentas.map { it.numeroCompleto() }
        binding.spCuentas.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, textos)

        val adapter = MovimientoAdapter(emptyList())
        binding.rvMovimientos.layoutManager = LinearLayoutManager(this)
        binding.rvMovimientos.adapter = adapter

        binding.spCuentas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val movimientos = (mbo?.getMovimientos(cuentas[pos]) ?: arrayListOf<Any>())
                    .filterIsInstance<Movimiento>()
                adapter.actualizar(movimientos)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}