package com.ort.isp.cryptoapp.framework.data.server

import com.ort.isp.cryptoapp.data.model.LoggedUserLocalData
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.TransactionDetail
import com.ort.isp.cryptoapp.data.model.out.TransactionCredential
import com.ort.isp.cryptoapp.data.source.RemoteTransactionDataSource
import javax.inject.Inject

class TransactionServerDataSource @Inject constructor(private val transactionService: TransactionService) :
    AbstractServerDataSource(), RemoteTransactionDataSource {
    override suspend fun getTransactionHistoryByUser(loggedUserLocalData: LoggedUserLocalData): Resource<List<TransactionDetail>> {
        return safeApiCall { transactionService.getTransactionsByUser(loggedUserLocalData.id) }
    }

    override suspend fun createTransaction(transactionCredential: TransactionCredential): Resource<Unit> {
        return safeApiCall { transactionService.createTransaction(transactionCredential) }
    }
}