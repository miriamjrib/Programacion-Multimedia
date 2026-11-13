package com.example.bancommjj.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bancommjj.R
import com.example.bancommjj.databinding.ActivityTransferBinding

class TransferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rgTipo.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbPropia) {
                binding.spDestino.visibility = View.VISIBLE
                binding.etDestino.visibility = View.GONE
            } else {
                binding.spDestino.visibility = View.GONE
                binding.etDestino.visibility = View.VISIBLE
            }
        }

        binding.btEnviar.setOnClickListener { enviar() }
        binding.btCancelar.setOnClickListener { cancelar() }
    }

    private fun enviar() {
        val origen = binding.spOrigen.selectedItem.toString()
        val propia = binding.rbPropia.isChecked
        var destino = binding.etDestino.text.toString().trim()
        if (propia) {
            destino = binding.spDestino.selectedItem.toString()
        }

        val importe = binding.etCantidad.text.toString().trim()
        val divisa = binding.spDivisa.selectedItem.toString()

        if (importe.isEmpty() || destino.isEmpty()) {
            Toast.makeText(this, R.string.faltan_datos, Toast.LENGTH_SHORT).show()
            return
        }

        var resumen = getString(R.string.resumen_origen) + "\n" + origen + "\n"
        if (propia) {
            resumen = resumen + getString(R.string.resumen_destino_propia) + "\n"
        } else {
            resumen = resumen + getString(R.string.resumen_destino_ajena) + "\n"
        }

        resumen = resumen + destino + "\n"
        resumen = resumen + getString(R.string.resumen_importe, importe, divisa)

        if (binding.cbJustificante.isChecked) {
            resumen = resumen + "\n" + getString(R.string.resumen_justificante)
        }
        Toast.makeText(this, resumen, Toast.LENGTH_LONG).show()
    }

    private fun cancelar() {
        binding.spOrigen.setSelection(0)
        binding.spDestino.setSelection(0)
        binding.spDivisa.setSelection(0)
        binding.rbPropia.isChecked = true
        binding.etDestino.text.clear()
        binding.etCantidad.text.clear()
        binding.cbJustificante.isChecked = false
    }
}