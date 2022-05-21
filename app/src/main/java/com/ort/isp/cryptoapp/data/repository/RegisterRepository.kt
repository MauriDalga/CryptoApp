package com.ort.isp.cryptoapp.data.repository

import com.ort.isp.cryptoapp.data.model.LoginCredential
import com.ort.isp.cryptoapp.data.model.RegisterCredential
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.source.RemoteRegisterDataSource
import com.ort.isp.cryptoapp.framework.data.model.LoggedInUser
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
class RegisterRepository @Inject constructor(
    private val remoteRegisterDataSource: RemoteRegisterDataSource,
    private val loginRepository: LoginRepository
) {

    suspend fun register(credential: RegisterCredential): Resource<LoggedInUser> {
        val result = remoteRegisterDataSource.register(credential)

        if (result is Resource.Success) {
            var loginCredencial = LoginCredential(credential.email, credential.password)
            val loginResult = loginRepository.login(loginCredencial)
            return loginResult
        }

        return Resource.Error(result.message.toString())
    }
}