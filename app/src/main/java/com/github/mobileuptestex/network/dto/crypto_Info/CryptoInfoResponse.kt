package com.github.mobileuptestex.network.dto.crypto_Info

data class CryptoInfoResponse(
  val name: String,
  val image: Image,
  val categories: List<String>,
  val description: Description,
)