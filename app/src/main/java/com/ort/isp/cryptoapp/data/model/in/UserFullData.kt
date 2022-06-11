package com.ort.isp.cryptoapp.data.model.`in`

data class UserFullData(
    val name: String,
    val lastname: String,
    val email: String,
    val token: String,
    val image: String?,
    val coinAccounts: List<CoinAccount>
)
