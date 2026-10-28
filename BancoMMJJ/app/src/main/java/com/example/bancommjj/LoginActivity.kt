package com.example.bancommjj

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bancommjj.bd.MiBancoOperacional
import com.example.bancommjj.databinding.ActivityLoginBinding
import com.example.bancommjj.pojo.Cliente

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEntrar.setOnClickListener {
            val usuario = binding.etUsuario.text.toString()
            val contrasena = binding.etContrasena.text.toString()
            var esValido = true

            if (usuario.isEmpty()) {
                binding.layoutUsuario.error = "Este campo es obligatorio"
                esValido = false
            } else {
                binding.layoutUsuario.error = null
            }

            if (contrasena.isEmpty()) {
                binding.layoutContrasena.error = "Este campo es obligatorio"
                esValido = false
            } else {
                binding.layoutContrasena.error = null
            }

            if (esValido) {
                val mbo = MiBancoOperacional.getInstance(this)
                val c = Cliente(0, usuario, null, null, contrasena, null)

                val cliente = mbo?.login(c)
                if (cliente == null) {
                    binding.layoutContrasena.error = getString(R.string.login_incorrecto)
                } else {
                    binding.layoutContrasena.error = null
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("Cliente", cliente)
                    startActivity(intent)
                }
            }
        }

        binding.btnSalir.setOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}