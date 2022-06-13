package com.ort.isp.cryptoapp.data.repository

import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.TransactionDetail
import com.ort.isp.cryptoapp.data.model.out.TransactionCredential
import com.ort.isp.cryptoapp.data.source.LocalSessionDataSource
import com.ort.isp.cryptoapp.data.source.RemoteTransactionDataSource
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val remoteTransactionDataSource: RemoteTransactionDataSource,
    private val localSessionDataSource: LocalSessionDataSource
) {
    suspend fun getTransactionHistoryByUser(): Resource<List<TransactionDetail>> {
        val userSession = localSessionDataSource.get()!!

        return remoteTransactionDataSource.getTransactionHistoryByUser(userSession)
    }

    suspend fun createTransaction(
        amount: Double,
        coinId: Int,
        receiverWalletAddress: String
    ): Resource<Nothing> {
        val userSession = localSessionDataSource.get()!!
        val transactionCredential = TransactionCredential(
            amount,
            coinId,
            userSession.id.toInt(),
            receiverWalletAddress
        )
        return remoteTransactionDataSource.createTransaction(transactionCredential)
    }
}