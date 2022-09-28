package com.github.mobileuptestex.ui.crypto_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mobileuptestex.ResponseState
import com.github.mobileuptestex.use_cases.CryptoInfoState
import com.github.mobileuptestex.use_cases.CryptoInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoInfoViewModel @Inject
constructor(private val cryptoInfoUseCase: CryptoInfoUseCase): ViewModel(){
  private val cryptoInfoValue = MutableStateFlow(CryptoInfoState())
  var _cryptoInfoValue : StateFlow<CryptoInfoState> = cryptoInfoValue

  fun getCryptoById(id: String) = viewModelScope.launch(Dispatchers.IO) {
    cryptoInfoUseCase(id).collect {
      when (it) {
        is ResponseState.Success -> {
          cryptoInfoValue.value = CryptoInfoState(cryptoInfo= it.data)
        }
        is ResponseState.Loading -> {
          cryptoInfoValue.value = CryptoInfoState(isLoading = true)
        }
        is ResponseState.Error -> {
          cryptoInfoValue.value = CryptoInfoState(error = it.message?: "Unexpected Error")
        }
      }
    }
  }

}