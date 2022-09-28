package com.github.mobileuptestex.ui.crypto_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mobileuptestex.utils.ResponseState
import com.github.mobileuptestex.use_cases.CryptoInfoState
import com.github.mobileuptestex.use_cases.CryptoInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoInfoViewModel @Inject
constructor(private val cryptoInfoUseCase: CryptoInfoUseCase): ViewModel(){
  private val mutableCryptoInfoValue = MutableStateFlow(CryptoInfoState())
  var cryptoInfoValue : StateFlow<CryptoInfoState> = mutableCryptoInfoValue

  fun getCryptoById(id: String) = viewModelScope.launch(Dispatchers.IO) {
    cryptoInfoUseCase(id).collect {
      when (it) {
        is ResponseState.Success -> {
          mutableCryptoInfoValue.value = CryptoInfoState(cryptoInfo= it.data)
        }
        is ResponseState.Loading -> {
          mutableCryptoInfoValue.value = CryptoInfoState(isLoading = true)
        }
        is ResponseState.Error -> {
          mutableCryptoInfoValue.value = CryptoInfoState(error = true)
        }
      }
    }
  }

}