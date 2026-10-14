package com.example.bancommjj

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bancommjj.databinding.ActivityMainBinding
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dni = intent.getStringExtra("DNI")
        binding.tvBienvenida.text = "Bienvenido/a, $dni"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnCambiarContrasena.setOnClickListener {
            val nuevaContrasena = EditText(this)
            nuevaContrasena.hint = "Nueva contraseña"

            AlertDialog.Builder(this)
                .setTitle("Cambiar contraseña")
                .setView(nuevaContrasena)
                .setPositiveButton("Aceptar") { dialog, _ ->
                    Toast.makeText(this, "Contraseña cambiada", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}