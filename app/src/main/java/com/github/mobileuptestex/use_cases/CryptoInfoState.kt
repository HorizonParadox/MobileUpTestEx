package com.github.mobileuptestex.use_cases

import com.github.mobileuptestex.network.dto.crypto_Info.CryptoInfoResponse
import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponseItem

data class CryptoInfoState(
  val isLoading: Boolean = false,
  val cryptoInfo : CryptoInfoResponse? = null,
  val error: String = ""
)
