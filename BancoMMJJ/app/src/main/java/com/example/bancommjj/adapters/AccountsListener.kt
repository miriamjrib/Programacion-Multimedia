package com.example.bancommjj.adapters

import com.example.bancommjj.pojo.Cuenta

interface AccountsListener {
    fun onAccountSelected(cuenta: Cuenta)
}