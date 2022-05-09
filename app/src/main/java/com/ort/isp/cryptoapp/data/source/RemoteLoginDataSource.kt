package com.ort.isp.cryptoapp.data.source

import com.ort.isp.cryptoapp.data.model.LoginCredential
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.framework.data.model.LoggedInUser

interface RemoteLoginDataSource {
    suspend fun login(credential: LoginCredential) : Resource<LoggedInUser>
}