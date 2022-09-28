package com.github.mobileuptestex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mobileuptestex.databinding.ActivityMainBinding
import com.github.mobileuptestex.utils.APP
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding =ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    APP = this
  }
}