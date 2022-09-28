package com.github.mobileuptestex.use_cases

import com.github.mobileuptestex.utils.ResponseState
import com.github.mobileuptestex.network.dto.crypto_list.CryptoListResponseItem
import com.github.mobileuptestex.di.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CryptoListUseCase @Inject
constructor(private val repository: CryptoRepository) {
  operator fun invoke(vsCurrency: String): Flow<ResponseState<List<CryptoListResponseItem>>> =
    flow {
      try {
        emit(ResponseState.Loading<List<CryptoListResponseItem>>())
        val cryptoItem = repository.getAllCrypto(vsCurrency)
        emit(ResponseState.Success<List<CryptoListResponseItem>>(cryptoItem))
      }
      catch (e:HttpException){
        emit(ResponseState.Error<List<CryptoListResponseItem>>("${e.localizedMessage} Error"))
      }
      catch (e: IOException){
        emit(ResponseState.Error<List<CryptoListResponseItem>>("Error"))
      }
    }
}