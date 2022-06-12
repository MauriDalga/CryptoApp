package com.ort.isp.cryptoapp.data.model.out

import com.ort.isp.cryptoapp.data.model.`in`.LoggedInUser

data class UpdatedUser(
    val name: String,
    val lastName: String,
    val email: String,
    val image: String
) {
    companion object {
        fun createFrom(registeredUser: LoggedInUser, photo: String) = UpdatedUser(
            registeredUser.name,
            registeredUser.lastname,
            registeredUser.email,
            photo
        )
    }
}
