package com.example.bancommjj

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bancommjj.databinding.ActivityLoginBinding

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
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("DNI", usuario)
                startActivity(intent)
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