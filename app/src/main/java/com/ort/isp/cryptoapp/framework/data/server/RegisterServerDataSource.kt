package com.ort.isp.cryptoapp.framework.data.server

import com.ort.isp.cryptoapp.data.model.RegisterCredential
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.source.RemoteRegisterDataSource
import com.ort.isp.cryptoapp.framework.data.model.RegisteredUser
import javax.inject.Inject

class RegisterServerDataSource @Inject constructor(private val registerService: RegisterService) :
    RemoteRegisterDataSource, AbstractServerDataSource() {

    override suspend fun register(credential: RegisterCredential): Resource<RegisteredUser> {
        return safeApiCall { registerService.register(credential) }
    }
}