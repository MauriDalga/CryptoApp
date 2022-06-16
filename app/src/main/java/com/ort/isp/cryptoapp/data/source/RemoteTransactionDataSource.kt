package com.ort.isp.cryptoapp.data.source

import com.ort.isp.cryptoapp.data.model.LoggedUserLocalData
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.TransactionDetail
import com.ort.isp.cryptoapp.data.model.out.TransactionCredential

interface RemoteTransactionDataSource {
    suspend fun getTransactionHistoryByUser(loggedUserLocalData: LoggedUserLocalData): Resource<List<TransactionDetail>>
    suspend fun createTransaction(transactionCredential: TransactionCredential): Resource<Unit>
}