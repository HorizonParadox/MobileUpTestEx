package com.github.mobileuptestex

import android.app.Application
import com.github.mobileuptestex.network.remote.crypto.CryptoApi
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class CryptoApp : Application()
