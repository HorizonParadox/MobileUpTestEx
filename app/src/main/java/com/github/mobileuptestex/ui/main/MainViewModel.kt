package com.github.mobileuptestex.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mobileuptestex.utils.ResponseState
import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponseItem
import com.github.mobileuptestex.use_cases.CryptoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(private val cryptoListUseCase: CryptoListUseCase) :ViewModel() {

  private val mutableCryptoListValue = MutableStateFlow(CryptoListState())
  val cryptoListValue: StateFlow<CryptoListState>
    get() = mutableCryptoListValue



  fun getCryptoList(vsCurrency: String) = viewModelScope.launch(Dispatchers.IO) {
    cryptoListUseCase(vsCurrency).collect {
      when (it) {
        is ResponseState.Success<List<CryptoListResponseItem>> -> {
          mutableCryptoListValue.value = CryptoListState(cryptoList = it.data ?: emptyList())
        }
        is ResponseState.Loading<List<CryptoListResponseItem>> -> {
          mutableCryptoListValue.value = CryptoListState(isLoading = true)
        }
        is ResponseState.Error<List<CryptoListResponseItem>> -> {
          mutableCryptoListValue.value = CryptoListState(error = true)
        }
      }
    }
  }
}