package com.ort.isp.cryptoapp.data.source

import com.ort.isp.cryptoapp.data.model.RegisterCredential
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.framework.data.model.RegisteredUser

interface RemoteRegisterDataSource {
    suspend fun register(credential: RegisterCredential) : Resource<RegisteredUser>
}