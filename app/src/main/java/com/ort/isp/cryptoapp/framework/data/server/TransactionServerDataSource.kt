package com.ort.isp.cryptoapp.framework.data.server

import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.LoggedInUser
import com.ort.isp.cryptoapp.data.model.`in`.TransactionDetail
import com.ort.isp.cryptoapp.data.source.RemoteTransactionDataSource
import javax.inject.Inject

class TransactionServerDataSource @Inject constructor(private val transactionService: TransactionService) :
    AbstractServerDataSource(), RemoteTransactionDataSource {
    override suspend fun getTransactionHistoryByUser(loggedInUser: LoggedInUser): Resource<List<TransactionDetail>> {
        return safeApiCall { transactionService.getTransactionsByUser(loggedInUser.id) }
    }
}