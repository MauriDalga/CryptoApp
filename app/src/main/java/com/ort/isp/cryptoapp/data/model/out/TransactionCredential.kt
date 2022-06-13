package com.ort.isp.cryptoapp.data.model.out

data class TransactionCredential(
    val amount: Double,
    val coinId: Int,
    val senderId: Int,
    val receiverWalletAddress: String
)
