package com.ort.isp.cryptoapp.data.model.`in`

data class LoggedInUser(
    val id: String,
    val name: String,
    val lastname: String,
    val email: String,
    val token: String,
    val walletAddress: String
) {
    companion object {
        fun createFrom(registeredUser: RegisteredUser) = LoggedInUser(
            registeredUser.id,
            registeredUser.name,
            registeredUser.lastname,
            registeredUser.email,
            registeredUser.token,
            registeredUser.walletAddress
        )
    }
}