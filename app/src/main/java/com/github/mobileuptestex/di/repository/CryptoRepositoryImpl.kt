package com.github.mobileuptestex.di.repository

import com.github.mobileuptestex.network.dto.crypto_Info.CryptoInfoResponse
import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponse
import com.github.mobileuptestex.network.remote.CryptoApi
import javax.inject.Inject

class CryptoRepositoryImpl @Inject
constructor(private val cryptoApi: CryptoApi) : CryptoRepository {
  override suspend fun getAllCrypto(vsCurrency: String): CryptoListResponse {
    return cryptoApi.getCryptoList(vsCurrency = vsCurrency)
  }

  override suspend fun getCryptoInfo(id: String): CryptoInfoResponse {
    return cryptoApi.getCryptoInfo(id = id)
  }
}