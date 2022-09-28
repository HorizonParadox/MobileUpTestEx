package com.github.mobileuptestex.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mobileuptestex.APP
import com.github.mobileuptestex.CryptoApp
import com.github.mobileuptestex.ResponseState
import com.github.mobileuptestex.adapter.CryptoAdapter
import com.github.mobileuptestex.databinding.FragmentMainBinding
import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponseItem
import com.github.mobileuptestex.network.remote.crypto.CryptoApi
import com.github.mobileuptestex.ui.crypto_info.CryptoInfoFragment
import com.google.android.material.chip.ChipGroup
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

    cryptoAdapter = CryptoAdapter(object : CryptoAdapter.OnItemClickListener {
      override fun onItemClick(info: String) {

        val fragment = CryptoInfoFragment.newInstance()
        val bundle = Bundle()
        bundle.putString("cryptoId", info)
        fragment.arguments = bundle

        activity!!.supportFragmentManager.beginTransaction()
          .replace(binding.mainFragment.id, fragment, "findThisFragment")
          .addToBackStack(null)
          .commit()
      }
    })

    viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    var currency = "usd"
    viewModel.getCryptoList(currency)

    binding.chipGroup.setOnCheckedStateChangeListener { group, _ ->
      when (group.checkedChipId) {
        2131361913 -> {
          currency = "usd"
          viewModel.getCryptoList(currency)
        }
        2131361912 -> {
          currency = "eur"
          viewModel.getCryptoList(currency)
        }
      }
    }


    binding.recyclerView.apply {
      adapter = cryptoAdapter
      layoutManager = LinearLayoutManager(activity)
      setHasFixedSize(true)
    }

    lifecycleScope.launchWhenStarted {
      viewModel._cryptoListValue.collect {

        if (it.isLoading)
          binding.progressBar.visibility = View.VISIBLE
        else
          binding.progressBar.visibility = View.GONE

        if (it.error!="") {
          binding.recyclerView.visibility = View.GONE
          binding.errorLayout.visibility = View.VISIBLE
          binding.errorButton.setOnClickListener{
            viewModel.getCryptoList(currency)
          }
        }
        else {
          binding.recyclerView.visibility = View.VISIBLE
          binding.errorLayout.visibility = View.GONE
        }
        cryptoAdapter.submitList(it.cryptoList)
      }
    }
  }

}