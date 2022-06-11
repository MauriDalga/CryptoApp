package com.ort.isp.cryptoapp.data.model.`in`

data class RegisteredUser(
    val id: String,
    val name: String,
    val lastName: String,
    val email: String,
    val token: String
)