package com.ort.isp.cryptoapp.data.model.`in`

data class CoinAccount(val coin: Coin, val balance: String)

data class Coin(val id: Int, val longName: String, val shortName: String, val icon: String)
