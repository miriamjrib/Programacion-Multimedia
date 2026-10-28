package com.example.bancommjj

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        cargarSpinner(binding.spOrigen, R.array.cuentas_transfer)
        cargarSpinner(binding.spDestino, R.array.cuentas_transfer)
        cargarSpinner(binding.spDivisa, R.array.divisas)

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

    private fun cargarSpinner(spinner: Spinner, arrayId: Int) {
        val adapter = ArrayAdapter.createFromResource(
            this, arrayId, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun enviar() {
        val origen = binding.spOrigen.selectedItem.toString()
        val propia = binding.rbPropia.isChecked
        val destino = if (propia) binding.spDestino.selectedItem.toString()
        else binding.etDestino.text.toString().trim()
        val importe = binding.etCantidad.text.toString().trim()
        val divisa = binding.spDivisa.selectedItem.toString()

        if (importe.isEmpty() || destino.isEmpty()) {
            Toast.makeText(this, R.string.faltan_datos, Toast.LENGTH_SHORT).show()
            return
        }

        val resumen = buildString {
            appendLine(getString(R.string.resumen_origen))
            appendLine(origen)
            appendLine(getString(if (propia) R.string.resumen_destino_propia else R.string.resumen_destino_ajena))
            appendLine(destino)
            appendLine(getString(R.string.resumen_importe, importe, divisa))
            if (binding.cbJustificante.isChecked) append(getString(R.string.resumen_justificante))
        }
        Toast.makeText(this, resumen, Toast.LENGTH_LONG).show()
    }

    private fun cancelar() {
        binding.spOrigen.setSelection(0)
        binding.spDestino.setSelection(0)
        binding.spDivisa.setSelection(0)
        binding.rbPropia.isChecked = true   // dispara el listener y restaura la vista
        binding.etDestino.text.clear()
        binding.etCantidad.text.clear()
        binding.cbJustificante.isChecked = false
    }
}