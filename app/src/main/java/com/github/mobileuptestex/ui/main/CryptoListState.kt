package com.github.mobileuptestex.ui.main

import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponseItem

data class CryptoListState(
  val isLoading: Boolean = false,
  val cryptoList : List<CryptoListResponseItem> = emptyList(),
  val error: String = ""
)
