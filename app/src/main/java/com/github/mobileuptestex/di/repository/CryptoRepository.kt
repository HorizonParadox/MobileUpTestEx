package com.github.mobileuptestex.di.repository

import com.github.mobileuptestex.network.dto.crypto_Info.CryptoInfoResponse
import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponse

interface CryptoRepository {

  suspend fun getAllCrypto(vsCurrency: String): CryptoListResponse

  suspend fun getCryptoInfo(id: String): CryptoInfoResponse

}