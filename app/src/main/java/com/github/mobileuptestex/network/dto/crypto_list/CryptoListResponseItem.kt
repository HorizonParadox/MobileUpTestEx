package com.github.mobileuptestex.network.dto.crypto_list

data class CryptoListResponseItem(
  val id: String,
  val name: String,
  val image: String,
  val symbol: String,
  val current_price: Double,
  val price_change_percentage_24h: Double,
)