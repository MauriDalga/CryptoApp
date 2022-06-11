package com.ort.isp.cryptoapp.data.source

import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.LoggedInUser
import com.ort.isp.cryptoapp.data.model.`in`.TransactionDetail

interface RemoteTransactionDataSource {
    suspend fun getTransactionHistoryByUser(loggedInUser: LoggedInUser): Resource<List<TransactionDetail>>
}