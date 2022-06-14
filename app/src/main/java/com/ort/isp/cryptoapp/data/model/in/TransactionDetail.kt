package com.ort.isp.cryptoapp.data.model.`in`

data class TransactionDetail(
    val id: String,
    val amount: Double,
    val senderId: String,
    val receiverId: String,
    val date: String,
    val coin: Coin
)