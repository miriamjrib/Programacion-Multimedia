package com.example.bancommjj.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bancommjj.adapters.CuentaAdapter
import com.example.bancommjj.bd.MiBancoOperacional
import com.example.bancommjj.databinding.FragmentAccountsBinding
import com.example.bancommjj.pojo.Cliente
import com.example.bancommjj.pojo.Cuenta

class AccountsFragment : Fragment() {

    private var _binding: FragmentAccountsBinding? = null
    private val binding get() = _binding!!

    private lateinit var cliente: Cliente

    companion object {
        private const val ARG_CLIENTE = "cliente"

        fun newInstance(c: Cliente): AccountsFragment {
            val fragment = AccountsFragment()

            fragment.arguments = Bundle().apply {
                putSerializable(ARG_CLIENTE, c)
            }

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cliente = arguments?.getSerializable(ARG_CLIENTE) as Cliente
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mbo = MiBancoOperacional.getInstance(requireContext())

        val cuentas = ArrayList<Cuenta>()
        val listaCuentas = mbo?.getCuentas(cliente)

        if (listaCuentas != null) {
            for (c in listaCuentas) {
                cuentas.add(c as Cuenta)
            }
        }

        binding.rvCuentas.layoutManager =
            LinearLayoutManager(requireContext())

        binding.rvCuentas.adapter = CuentaAdapter(cuentas)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
