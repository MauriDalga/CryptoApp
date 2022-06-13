package com.ort.isp.cryptoapp.data.model.`in`

data class RegisteredUser(
    val id: String,
    val name: String,
    val lastname: String,
    val email: String,
    val token: String,
    val walletAddress: String
)