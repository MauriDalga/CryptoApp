package com.ort.isp.cryptoapp.data.model.out

import com.ort.isp.cryptoapp.data.model.LoggedUserLocalData

data class UpdatedUser(
    val name: String,
    val lastname: String,
    val email: String,
    val image: String,
) {
    companion object {
        fun createFrom(loggedUserLocalData: LoggedUserLocalData, photo: String) = UpdatedUser(
            loggedUserLocalData.name,
            loggedUserLocalData.lastname,
            loggedUserLocalData.email,
            photo,
        )
    }
}
