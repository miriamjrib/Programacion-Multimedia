package com.example.bancommjj.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bancommjj.R
import com.example.bancommjj.adapters.CuentaAdapter
import com.example.bancommjj.bd.MiBancoOperacional
import com.example.bancommjj.databinding.ActivityGlobalPositionBinding
import com.example.bancommjj.pojo.Cliente
import com.example.bancommjj.pojo.Cuenta

class GlobalPositionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGlobalPositionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGlobalPositionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cliente = intent.getSerializableExtra("Cliente") as Cliente
        val mbo = MiBancoOperacional.getInstance(this)

        val cuentas = ArrayList<Cuenta>()
        val listaCuentas = mbo?.getCuentas(cliente)
        if (listaCuentas != null) {
            for (c in listaCuentas) {
                cuentas.add(c as Cuenta)
            }
        }

        binding.rvCuentas.layoutManager = LinearLayoutManager(this)
        binding.rvCuentas.adapter = CuentaAdapter(cuentas)
    }
}