package com.ort.isp.cryptoapp.data.source

import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.RegisteredUser
import com.ort.isp.cryptoapp.data.model.out.RegisterCredential

interface RemoteRegisterDataSource {
    suspend fun register(credential: RegisterCredential) : Resource<RegisteredUser>
}