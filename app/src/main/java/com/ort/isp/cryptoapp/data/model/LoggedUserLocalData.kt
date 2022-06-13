package com.ort.isp.cryptoapp.data.model

data class LoggedUserLocalData(
    val id: String,
    val name: String,
    val lastname: String,
    val email: String,
    val token: String,
    val walletAddress: String,
    val cachedQrImageUri: String
)
