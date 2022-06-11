package com.ort.isp.cryptoapp.data.model.`in`

data class LoggedInUser(
    val id: String,
    val name: String,
    val lastname: String,
    val email: String,
    val token: String
) {
    companion object {
        fun createFrom(registeredUser: RegisteredUser) = LoggedInUser(
            registeredUser.id,
            registeredUser.name,
            registeredUser.lastName,
            registeredUser.email,
            registeredUser.token
        )
    }
}