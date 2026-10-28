package com.example.bancommjj

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bancommjj.databinding.ItemMovimientoBinding
import com.example.bancommjj.pojo.Movimiento
import java.text.SimpleDateFormat
import java.util.Locale

class MovimientoAdapter(private var movimientos: List<Movimiento>) :
    RecyclerView.Adapter<MovimientoAdapter.ViewHolder>() {

    class ViewHolder(val b: ItemMovimientoBinding) : RecyclerView.ViewHolder(b.root)
    fun actualizar(nuevos: List<Movimiento>) {
        movimientos = nuevos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val b = ItemMovimientoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(b)
    }

    override fun getItemCount() = movimientos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val m = movimientos[position]
        val ctx = holder.itemView.context
        val importe = m.getImporte()
        val formato = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val fecha = m.getFechaOperacion()?.let { formato.format(it) } ?: ""
        val importeTexto = String.format(Locale.getDefault(), "%.2f", importe)

        holder.b.tvDescripcion.text = m.getDescripcion()
        holder.b.tvFechaImporte.text =
            "$fecha  " + ctx.getString(R.string.importe_formato, importeTexto)

        val colorId = if (importe < 0) R.color.banco_negativo else R.color.banco_positivo
        holder.b.tvFechaImporte.setTextColor(ContextCompat.getColor(ctx, colorId))
    }
}