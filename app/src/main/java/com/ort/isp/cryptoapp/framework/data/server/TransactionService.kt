package com.ort.isp.cryptoapp.framework.data.server

import com.ort.isp.cryptoapp.data.model.`in`.TransactionDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TransactionService {
    @GET("users/{userId}/transactions")
    suspend fun getTransactionsByUser(@Path("userId") userId: String): Response<List<TransactionDetail>>
}