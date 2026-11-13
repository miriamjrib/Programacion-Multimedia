package com.example.bancommjj.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bancommjj.adapters.MovimientoAdapter
import com.example.bancommjj.databinding.FragmentAccountsMovementsBinding
import com.example.bancommjj.pojo.Cuenta
import com.example.bancommjj.bd.MiBancoOperacional
import com.example.bancommjj.pojo.Movimiento

class AccountsMovementsFragment : Fragment() {

    private var _binding: FragmentAccountsMovementsBinding? = null
    private val binding get() = _binding!!

    private lateinit var cuenta: Cuenta

    companion object {
        private const val ARG_CUENTA = "cuenta"

        fun newInstance(c: Cuenta): AccountsMovementsFragment {
            val fragment = AccountsMovementsFragment()

            fragment.arguments = Bundle().apply {
                putSerializable(ARG_CUENTA, c)
            }

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cuenta = arguments?.getSerializable(ARG_CUENTA) as Cuenta
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountsMovementsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mbo = MiBancoOperacional.getInstance(requireContext())

        val movimientos = ArrayList<Movimiento>()
        val listaMovimientos = mbo?.getMovimientos(cuenta)

        if (listaMovimientos != null) {
            for (m in listaMovimientos) {
                movimientos.add(m as Movimiento)
            }
        }

        binding.rvMovimientos.layoutManager =
            LinearLayoutManager(requireContext())

        binding.rvMovimientos.adapter = MovimientoAdapter(movimientos)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
