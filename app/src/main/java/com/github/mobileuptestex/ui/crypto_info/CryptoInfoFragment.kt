package com.github.mobileuptestex.ui.crypto_info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.github.mobileuptestex.APP
import com.github.mobileuptestex.databinding.CryptoInfoFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup
import org.jsoup.safety.Safelist
import java.util.regex.Pattern

@AndroidEntryPoint
class CryptoInfoFragment : Fragment() {

  private lateinit var binding: CryptoInfoFragmentBinding
  private lateinit var viewModel: CryptoInfoViewModel

  companion object {
    fun newInstance() = CryptoInfoFragment()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = CryptoInfoFragmentBinding.inflate(layoutInflater, container, false)
    APP.setSupportActionBar(binding.myToolbar)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val bundle = this.arguments
    val idString = bundle?.getString("cryptoId")
    Log.e("CryptoID", idString.toString())

    viewModel = ViewModelProvider(this)[CryptoInfoViewModel::class.java]

    if (idString != null) {
      viewModel.getCryptoById(idString)
    }

    binding.apply {
      lifecycleScope.launchWhenStarted {
        viewModel._cryptoInfoValue.collect {

          if (it.isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.descriptionTV.visibility = View.GONE
            binding.categoryTV.visibility = View.GONE
          } else {
            binding.progressBar.visibility = View.GONE
            binding.descriptionTV.visibility = View.VISIBLE
            binding.categoryTV.visibility = View.VISIBLE
          }
          if (it.error != "") {
            binding.errorLayout.visibility = View.VISIBLE
            binding.descriptionTV.visibility = View.GONE
            binding.categoryTV.visibility = View.GONE
            binding.errorButton.setOnClickListener {
              //НЕ РАБОТАЕТ КНОПКА ИЗ_ЗА ТРАНЗАКЦИЙ
              if (idString != null) {
                viewModel.getCryptoById(idString)
              }
            }
          } else {
            binding.errorLayout.visibility = View.GONE
          }

          if (it.cryptoInfo != null) {
            APP.supportActionBar?.title = it.cryptoInfo.name
            Glide.with(logoInfoIV).load(it.cryptoInfo.image.large).into(logoInfoIV)
            descriptionInfoTV.text = Jsoup.parse(it.cryptoInfo.description.en).text()
            categoryInfoTV.text =
              it.cryptoInfo.categories.toString().replace("[", "").replace("]", "")
          }
        }
      }
    }
  }
}