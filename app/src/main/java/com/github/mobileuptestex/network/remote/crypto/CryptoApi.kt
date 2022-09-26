package com.github.mobileuptestex.network.remote.crypto

import com.github.mobileuptestex.network.dto.crypto_Info.CryptoInfoResponse
import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponse
import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoApi {

  @GET("/api/v3/coins/markets/")
  suspend fun getCryptoList(@Query("vs_currency") vsCurrency: String): CryptoListResponse

  @GET("/api/v3/coins/{id}")
  suspend fun getCryptoInfo(@Path("id") id: String): CryptoInfoResponse
}