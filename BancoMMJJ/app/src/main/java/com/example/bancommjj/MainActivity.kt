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
        cliente = intent.getSerializableExtra("Cliente") as Cliente
        binding.tvBienvenida.text = getString(R.string.bienvenido_cliente, cliente.getNombre())
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnPosicionGlobal.setOnClickListener { abrir(GlobalPositionActivity::class.java) }
        binding.btnMovimientos.setOnClickListener { abrir(MovementsActivity::class.java) }
        binding.btnSalirMain.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.confirmar_salir)
                .setPositiveButton(android.R.string.ok) { dialog, which -> finish() }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
        binding.btnTransferencias.setOnClickListener { abrir(TransferActivity::class.java) }

        binding.btnPromociones.setOnClickListener {
            Toast.makeText(this, R.string.proximamente, Toast.LENGTH_SHORT).show()
        }
        binding.btnCajeros.setOnClickListener {
            Toast.makeText(this, R.string.proximamente, Toast.LENGTH_SHORT).show()
        }

        binding.btnCambiarContrasena.setOnClickListener {
            val nuevaContrasena = EditText(this)
            nuevaContrasena.hint = getString(R.string.nueva_contrasena)

            AlertDialog.Builder(this)
                .setTitle(R.string.cambiar_contrasena_titulo)
                .setView(nuevaContrasena)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    val nueva = nuevaContrasena.text.toString()
                    if (nueva.isBlank()) {
                        Toast.makeText(this, R.string.contrasena_vacia, Toast.LENGTH_SHORT).show()
                    } else if (nueva.length < 4) {
                        Toast.makeText(this, R.string.contrasena_corta, Toast.LENGTH_SHORT).show()
                    } else if (nueva == cliente.getClaveSeguridad()) {
                        Toast.makeText(this, R.string.contrasena_igual, Toast.LENGTH_SHORT).show()
                    } else {
                        val claveAntigua = cliente.getClaveSeguridad()
                        cliente.setClaveSeguridad(nueva)

                        val mbo = MiBancoOperacional.getInstance(this)
                        var resultado = 0
                        if (mbo != null) {
                            resultado = mbo.changePassword(cliente)
                        }

                        if (resultado == 1) {
                            Toast.makeText(this, R.string.contrasena_cambiada, Toast.LENGTH_SHORT).show()
                        } else {
                            cliente.setClaveSeguridad(claveAntigua)
                            Toast.makeText(this, R.string.contrasena_error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
    }

    private fun abrir(destino: Class<*>) {
        startActivity(Intent(this, destino).putExtra("Cliente", cliente))
    }
}