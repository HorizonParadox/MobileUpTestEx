package com.github.mobileuptestex.di

import com.github.mobileuptestex.network.remote.CryptoApi
import com.github.mobileuptestex.di.repository.CryptoRepository
import com.github.mobileuptestex.di.repository.CryptoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoModule {

  @Provides
  @Singleton
  fun provideCryptoApi(okHttpClient: OkHttpClient): CryptoApi = Retrofit.Builder()
    .baseUrl("https://api.coingecko.com")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(CryptoApi::class.java)

  @Provides
  @Singleton
  fun provideCryptoRepository(api: CryptoApi): CryptoRepository = CryptoRepositoryImpl(api)

  @Provides
  @Singleton
  fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .build()

  @Provides
  @Singleton
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
  }
}