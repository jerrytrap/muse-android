package com.muse1991.museapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.muse1991.museapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBinding()
        setFragment()
    }

    private fun setBinding() {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    private fun setFragment() {
        supportFragmentManager.beginTransaction().add(binding.navHostFragment.id, SignInFragment())
    }
}