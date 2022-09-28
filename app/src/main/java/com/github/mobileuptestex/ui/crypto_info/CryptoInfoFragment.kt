package com.github.mobileuptestex.ui.crypto_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.github.mobileuptestex.databinding.CryptoInfoFragmentBinding
import com.github.mobileuptestex.utils.APP
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup

@AndroidEntryPoint
class CryptoInfoFragment : Fragment() {

  private lateinit var binding: CryptoInfoFragmentBinding
  private lateinit var viewModel: CryptoInfoViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = CryptoInfoFragmentBinding.inflate(layoutInflater, container, false)
    APP.setSupportActionBar(binding.cryptoFragmentToolbar)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initialization()
  }

  private fun initialization(){
    val args: CryptoInfoFragmentArgs by navArgs()
    val cryptoIdAndName = args.cryptoId

    configureSupportActionBar(cryptoIdAndName.name)

    viewModel = ViewModelProvider(this)[CryptoInfoViewModel::class.java]
    viewModel.getCryptoById(cryptoIdAndName.id)

    binding.errorButton.setOnClickListener {
      viewModel.getCryptoById(cryptoIdAndName.id)
    }

    lifecycleScope.launchWhenStarted {
      viewModel.cryptoInfoValue.collect {

        if (it.isLoading) loadView(binding)
        else binding.progressBar.visibility = View.GONE

        if (it.error) errorView(binding)
        else binding.errorLayout.visibility = View.GONE

        if (it.cryptoInfo != null) {
          dataView(binding)

          val description = it.cryptoInfo.description.en
          val categories = it.cryptoInfo.categories.toString().replace("[", "").replace("]", "")

          Glide.with(binding.logoInfoIV).load(it.cryptoInfo.image.large).into(binding.logoInfoIV)

          binding.descriptionInfoTV.text =
            Jsoup.parse(description).text()
              .ifEmpty { "Cryptocurrency description in English is not available!" }

          binding.categoryInfoTV.text =
            categories.ifEmpty { "Cryptocurrency categories not found!" }
        }
      }
    }
  }

  private fun loadView(binding: CryptoInfoFragmentBinding) {
    binding.progressBar.visibility = View.VISIBLE
    binding.descriptionTV.visibility = View.GONE
    binding.categoryTV.visibility = View.GONE
    binding.errorLayout.visibility = View.GONE
  }

  private fun errorView(binding: CryptoInfoFragmentBinding) {
    binding.progressBar.visibility = View.GONE
    binding.descriptionTV.visibility = View.GONE
    binding.categoryTV.visibility = View.GONE
    binding.errorLayout.visibility = View.VISIBLE
  }

  private fun dataView(binding: CryptoInfoFragmentBinding) {
    binding.progressBar.visibility = View.GONE
    binding.descriptionTV.visibility = View.VISIBLE
    binding.categoryTV.visibility = View.VISIBLE
    binding.errorLayout.visibility = View.GONE
  }

  private fun configureSupportActionBar(nameString: String) {
    APP.supportActionBar?.title = nameString
    APP.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    binding.cryptoFragmentToolbar.setNavigationOnClickListener { findNavController().popBackStack() }
  }
}