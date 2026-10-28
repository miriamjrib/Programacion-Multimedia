package com.example.bancommjj

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bancommjj.databinding.ItemCuentaBinding
import com.example.bancommjj.pojo.Cuenta
import java.util.Locale

fun Cuenta.numeroCompleto(): String =
    "${getBanco()}-${getSucursal()}-${getDc()}-${getNumeroCuenta()}"

class CuentaAdapter(private val cuentas: List<Cuenta>) :
    RecyclerView.Adapter<CuentaAdapter.ViewHolder>() {

    class ViewHolder(val b: ItemCuentaBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val b = ItemCuentaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(b)
    }

    override fun getItemCount() = cuentas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cuenta = cuentas[position]
        val saldo = cuenta.getSaldoActual()
        val colorId = if (saldo != null && saldo < 0) R.color.banco_negativo else R.color.banco_positivo

        holder.b.tvNumCuenta.text = cuenta.numeroCompleto()
        holder.b.tvSaldo.text = String.format(Locale.getDefault(), "%.2f", saldo)
        holder.b.tvSaldo.setTextColor(ContextCompat.getColor(holder.itemView.context, colorId))
    }
}