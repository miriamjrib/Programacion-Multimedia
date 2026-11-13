
package com.example.bancommjj.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bancommjj.adapters.AccountsListener
import com.example.bancommjj.databinding.ActivityGlobalPositionBinding
import com.example.bancommjj.fragments.AccountsFragment
import com.example.bancommjj.pojo.Cliente
import com.example.bancommjj.pojo.Cuenta
import android.content.Intent

class GlobalPositionActivity : AppCompatActivity(), AccountsListener {

    private lateinit var binding: ActivityGlobalPositionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGlobalPositionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        // Recuperamos el cliente que nos llega desde la otra Activity
        val cliente = intent.getSerializableExtra("Cliente") as Cliente

        // Cargamos el Fragment solamente la primera vez
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    binding.fragmentContainer.id,
                    AccountsFragment.newInstance(cliente)
                )
                .commit()
        }
    }

    override fun onAccountSelected(cuenta: Cuenta) {
        val intent = Intent(this, GlobalPositionDetailsActivity::class.java)
        intent.putExtra("Cuenta", cuenta)
        startActivity(intent)
    }
}
