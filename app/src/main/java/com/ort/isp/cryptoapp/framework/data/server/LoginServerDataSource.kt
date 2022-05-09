package com.ort.isp.cryptoapp.framework.data.server

import com.ort.isp.cryptoapp.data.model.LoginCredential
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.source.RemoteLoginDataSource
import com.ort.isp.cryptoapp.framework.data.model.LoggedInUser
import javax.inject.Inject

class LoginServerDataSource @Inject constructor(private val loginService: LoginService) :
    RemoteLoginDataSource, AbstractServerDataSource() {

    override suspend fun login(credential: LoginCredential): Resource<LoggedInUser> {
        return safeApiCall { loginService.login(credential) }
    }
}