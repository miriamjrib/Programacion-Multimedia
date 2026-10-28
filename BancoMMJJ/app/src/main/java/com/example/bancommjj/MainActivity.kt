package com.example.bancommjj

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bancommjj.databinding.ActivityMainBinding
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.IntentCompat
import com.example.bancommjj.pojo.Cliente
import com.example.bancommjj.bd.MiBancoOperacional

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cliente = IntentCompat.getSerializableExtra(intent, "Cliente", Cliente::class.java)
            ?: run { finish(); return }
        binding.tvBienvenida.text = getString(R.string.bienvenido_cliente, cliente.getNombre())
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnPosicionGlobal.setOnClickListener { abrir(GlobalPositionActivity::class.java) }
        binding.btnMovimientos.setOnClickListener { abrir(MovementsActivity::class.java) }
        binding.btnSalirMain.setOnClickListener { finish() }
        binding.btnTransferencias.setOnClickListener { abrir(TransferActivity::class.java) }

        binding.btnCambiarContrasena.setOnClickListener {
            val nuevaContrasena = EditText(this)
            nuevaContrasena.hint = "Nueva contraseña"

            AlertDialog.Builder(this)
                .setTitle("Cambiar contraseña")
                .setView(nuevaContrasena)
                .setPositiveButton("Aceptar") { _, _ ->
                    val nueva = nuevaContrasena.text.toString()
                    if (nueva.isBlank()) {
                        Toast.makeText(this, R.string.contrasena_vacia, Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    val c = Cliente(0, cliente.getNif(), null, null, nueva, null)
                    val resultado = MiBancoOperacional.getInstance(this)?.changePassword(c) ?: 0

                    if (resultado == 1) {
                        cliente.setClaveSeguridad(nueva)
                        Toast.makeText(this, R.string.contrasena_cambiada, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, R.string.contrasena_error, Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun abrir(destino: Class<*>) {
        startActivity(Intent(this, destino).putExtra("Cliente", cliente))
    }
}