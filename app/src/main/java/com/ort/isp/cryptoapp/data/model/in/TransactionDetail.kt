package com.ort.isp.cryptoapp.data.model.`in`

import java.util.Date

data class TransactionDetail(val operation: String, val amount: Double, val coinName: String, val date: Date, val walletAddress: String)