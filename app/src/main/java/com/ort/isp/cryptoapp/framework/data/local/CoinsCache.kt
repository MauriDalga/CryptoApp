package com.ort.isp.cryptoapp.framework.data.local

import com.ort.isp.cryptoapp.data.model.`in`.Coin
import com.ort.isp.cryptoapp.data.model.`in`.UserFullData

object CoinsCache {
    private val coins: MutableList<Coin> = ArrayList()

    fun updateCacheWith(userFullData: UserFullData) {
        coins.clear()
        for (coinAccount in userFullData.coinAccounts) {
            coins.add(coinAccount.coin)
        }
    }

    fun getCoins() = coins

    fun getCoinIdByCoinName(coinName: String): Int {
        for (coin in coins) {
            if (coin.longName == coinName) return coin.id
        }
        return 0
    }
}