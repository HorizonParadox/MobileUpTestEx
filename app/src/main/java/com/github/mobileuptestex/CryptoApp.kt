package com.github.mobileuptestex

import android.app.Application
import com.github.mobileuptestex.network.remote.crypto.CryptoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoApp : Application() {

  lateinit var cryptoApi: CryptoApi

  override fun onCreate() {
    super.onCreate()

    configureRetrofit()
  }

  private fun configureRetrofit() {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val okHttpClient = OkHttpClient.Builder()
      .addInterceptor(httpLoggingInterceptor)
      .build()

    val retrofit = Retrofit.Builder()
      .baseUrl("https://api.coingecko.com")
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    cryptoApi = retrofit.create(CryptoApi::class.java)
  }

}