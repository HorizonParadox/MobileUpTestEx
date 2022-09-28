package com.github.mobileuptestex.use_cases

import com.github.mobileuptestex.utils.ResponseState
import com.github.mobileuptestex.network.dto.crypto_Info.CryptoInfoResponse
import com.github.mobileuptestex.di.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CryptoInfoUseCase @Inject
constructor(private val repository: CryptoRepository) {
  operator fun invoke(id: String): Flow<ResponseState<CryptoInfoResponse>> =
    flow {
      try {
        emit(ResponseState.Loading())
        val cryptoInfo = repository.getCryptoInfo(id)
        emit(ResponseState.Success<CryptoInfoResponse>(cryptoInfo))
      }
      catch (e: HttpException){
        emit(ResponseState.Error<CryptoInfoResponse>("${e.localizedMessage} Error"))
      }
      catch (e: IOException){
        emit(ResponseState.Error<CryptoInfoResponse>("Error"))
      }
    }
}