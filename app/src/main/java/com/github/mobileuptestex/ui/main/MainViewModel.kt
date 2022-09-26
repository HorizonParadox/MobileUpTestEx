package com.github.mobileuptestex.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.mobileuptestex.network.remote.crypto.CryptoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {

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
}