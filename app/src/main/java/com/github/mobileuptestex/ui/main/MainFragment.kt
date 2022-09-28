package com.github.mobileuptestex.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mobileuptestex.utils.APP
import com.github.mobileuptestex.ui.main.recycler_view.CryptoAdapter
import com.github.mobileuptestex.databinding.FragmentMainBinding
import com.github.mobileuptestex.ui.SaveArgsModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

  private lateinit var binding: FragmentMainBinding
  private lateinit var cryptoAdapter: CryptoAdapter
  private var currency = "usd"
  private lateinit var viewModel: MainViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMainBinding.inflate(layoutInflater, container, false)
    APP.setSupportActionBar(binding.mainFragmentToolbar)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initialization()
  }

  private fun initialization(){
    viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    val chipUsdId = binding.chipUSD.id
    val chipEurId = binding.chipEUR.id

    viewModel.getCryptoList(currency)

    cryptoAdapter = CryptoAdapter(object : CryptoAdapter.OnItemClickListener {
      override fun onItemClick(info: SaveArgsModel) {

        val action = MainFragmentDirections.actionMainFragmentToCryptoInfoFragment(info)
        findNavController().navigate(action)

      }
    })

    binding.chipGroup.setOnCheckedStateChangeListener { group, _ ->

      when (group.checkedChipId) {
        chipUsdId -> {
          currency = "usd"
          viewModel.getCryptoList(currency)
        }
        chipEurId -> {
          currency = "eur"
          viewModel.getCryptoList(currency)
        }
      }
    }

    binding.errorButton.setOnClickListener {
      viewModel.getCryptoList(currency)
    }

    binding.recyclerView.apply {
      adapter = cryptoAdapter
      layoutManager = LinearLayoutManager(activity)
      setHasFixedSize(true)
    }

    lifecycleScope.launchWhenStarted {
      viewModel.cryptoListValue.collect {

        if (it.isLoading) loadView(binding)
        else binding.progressBar.visibility = View.GONE

        if (it.error) errorView(binding)
        else binding.errorLayout.visibility = View.GONE

        if (it.cryptoList.isNotEmpty()) {
          dataView(binding)
          cryptoAdapter.submitList(it.cryptoList, currency)
        }
      }
    }
  }

  private fun loadView(binding: FragmentMainBinding) {
    binding.progressBar.visibility = View.VISIBLE
    binding.recyclerView.visibility = View.GONE
    binding.errorLayout.visibility = View.GONE
  }

  private fun errorView(binding: FragmentMainBinding) {
    binding.errorLayout.visibility = View.VISIBLE
    binding.recyclerView.visibility = View.GONE
    binding.progressBar.visibility = View.GONE
  }

  private fun dataView(binding: FragmentMainBinding) {
    binding.recyclerView.visibility = View.VISIBLE
    binding.progressBar.visibility = View.GONE
    binding.errorLayout.visibility = View.GONE
  }
}