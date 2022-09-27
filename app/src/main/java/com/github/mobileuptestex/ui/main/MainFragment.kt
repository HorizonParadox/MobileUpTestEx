package com.github.mobileuptestex.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mobileuptestex.APP
import com.github.mobileuptestex.CryptoApp
import com.github.mobileuptestex.ResponseState
import com.github.mobileuptestex.adapter.CryptoAdapter
import com.github.mobileuptestex.databinding.FragmentMainBinding
import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponseItem
import com.github.mobileuptestex.network.remote.crypto.CryptoApi
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainFragment : Fragment() {

  private lateinit var binding: FragmentMainBinding
  private lateinit var cryptoAdapter: CryptoAdapter

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

    cryptoAdapter = CryptoAdapter()

    viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    viewModel.getCryptoList("usd")

    binding.recyclerView.apply {
      adapter = cryptoAdapter
      layoutManager = LinearLayoutManager(activity)
      setHasFixedSize(true)
    }

    lifecycleScope.launchWhenStarted {
      viewModel._cryptoListValue.collect {
        cryptoAdapter.submitList(it.cryptoList)
      }
    }
  }

}