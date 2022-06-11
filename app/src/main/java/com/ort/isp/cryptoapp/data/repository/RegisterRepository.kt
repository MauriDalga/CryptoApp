package com.ort.isp.cryptoapp.data.repository

import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.LoggedInUser
import com.ort.isp.cryptoapp.data.model.`in`.RegisteredUser
import com.ort.isp.cryptoapp.data.model.out.RegisterCredential
import com.ort.isp.cryptoapp.data.source.RemoteRegisterDataSource
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val remoteRegisterDataSource: RemoteRegisterDataSource,
    private val loginRepository: LoginRepository
) {

    suspend fun register(credential: RegisterCredential): Resource<RegisteredUser> {
        val result = remoteRegisterDataSource.register(credential)

        if (result is Resource.Success) {
            loginRepository.setLoggedInUser(LoggedInUser.createFrom(result.data!!))
        }

        return result
    }
}