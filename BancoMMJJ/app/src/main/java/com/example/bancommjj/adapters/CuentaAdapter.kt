package com.example.bancommjj.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bancommjj.R
import com.example.bancommjj.databinding.ItemCuentaBinding
import com.example.bancommjj.pojo.Cuenta
import java.util.Locale

class CuentaAdapter(
    private val cuentas: List<Cuenta>,
    private val listener: AccountsListener
) : RecyclerView.Adapter<CuentaAdapter.ViewHolder>() {

    class ViewHolder(val b: ItemCuentaBinding) :
        RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val b = ItemCuentaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(b)
    }

    override fun getItemCount(): Int {
        return cuentas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cuenta = cuentas[position]
        val saldo = cuenta.getSaldoActual() ?: 0f
        val contexto = holder.itemView.context

        holder.b.tvNumCuenta.text = cuenta.getNumeroCompleto()
        holder.b.tvSaldo.text =
            String.format(Locale.getDefault(), "%.2f", saldo)

        if (saldo < 0) {
            holder.b.tvSaldo.setTextColor(
                ContextCompat.getColor(contexto, R.color.banco_negativo)
            )
        } else {
            holder.b.tvSaldo.setTextColor(
                ContextCompat.getColor(contexto, R.color.banco_positivo)
            )
        }

        holder.itemView.setOnClickListener {
            listener.onAccountSelected(cuenta)
        }
    }
}
