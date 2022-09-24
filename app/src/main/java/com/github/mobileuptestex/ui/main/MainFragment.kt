package com.github.mobileuptestex.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mobileuptestex.APP
import com.github.mobileuptestex.databinding.FragmentMainBinding


class MainFragment : Fragment() {

  private lateinit var binding: FragmentMainBinding

  companion object {
    fun newInstance() = MainFragment()
  }

  private lateinit var viewModel: MainViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMainBinding.inflate(layoutInflater, container, false)
    APP.setSupportActionBar(binding.myToolbar)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    // TODO: Use the ViewModel
  }

}