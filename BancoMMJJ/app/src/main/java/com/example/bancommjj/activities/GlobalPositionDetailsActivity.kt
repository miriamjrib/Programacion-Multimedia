package com.example.bancommjj.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bancommjj.databinding.ActivityGlobalPositionDetailsBinding
import com.example.bancommjj.fragments.AccountsMovementsFragment
import com.example.bancommjj.pojo.Cuenta

class GlobalPositionDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGlobalPositionDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGlobalPositionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cuenta = intent.getSerializableExtra("Cuenta") as Cuenta

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    binding.fragmentContainer.id,
                    AccountsMovementsFragment.newInstance(cuenta)
                )
                .commit()
        }
    }
}
