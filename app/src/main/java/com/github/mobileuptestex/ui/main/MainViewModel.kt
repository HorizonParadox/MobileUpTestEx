package com.github.mobileuptestex.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mobileuptestex.ResponseState
import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponseItem
import com.github.mobileuptestex.network.remote.crypto.CryptoApi
import com.github.mobileuptestex.use_cases.CryptoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(private val cryptoListUseCase: CryptoListUseCase) :ViewModel() {

  val cryptoListValue = MutableStateFlow(CryptoListState())
  val _cryptoListValue: StateFlow<CryptoListState>
    get() = cryptoListValue



  fun getCryptoList(vsCurrency: String) = viewModelScope.launch(Dispatchers.IO) {
    cryptoListUseCase(vsCurrency).collect {
      when (it) {
        is ResponseState.Success<List<CryptoListResponseItem>> -> {
          cryptoListValue.value = CryptoListState(cryptoList = it.data ?: emptyList())
        }
        is ResponseState.Loading<List<CryptoListResponseItem>> -> {
          cryptoListValue.value = CryptoListState(isLoading = true)
        }
        is ResponseState.Error<List<CryptoListResponseItem>> -> {
          cryptoListValue.value = CryptoListState(error = it.message ?: "Unexpected Error")
        }
      }
    }
  }


/*
  fun fetchCryptoList(cryptoApi: CryptoApi){
      viewModelScope.launch(Dispatchers.IO) {
        try {
          Log.e("LIST", cryptoApi.getCryptoList("usd").toString())
          Log.e("INFO", cryptoApi.getCryptoInfo("bitcoin").toString())

        }catch (e: Exception){
          Log.e("TAG", "Exception  during request -> ${e.localizedMessage}")
        }
      }
  }
  */

}