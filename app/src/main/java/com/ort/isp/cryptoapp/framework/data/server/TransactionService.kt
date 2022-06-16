package com.ort.isp.cryptoapp.framework.data.server

import com.ort.isp.cryptoapp.data.model.`in`.TransactionDetail
import com.ort.isp.cryptoapp.data.model.out.TransactionCredential
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TransactionService {
    @GET("transactions")
    suspend fun getTransactionsByUser(@Query("userId") userId: String): Response<List<TransactionDetail>>

    @POST("transactions")
    suspend fun createTransaction(@Body transactionCredential: TransactionCredential): Response<Unit>
}