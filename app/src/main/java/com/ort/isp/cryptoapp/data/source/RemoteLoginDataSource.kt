package com.ort.isp.cryptoapp.data.source

import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.LoggedInUser
import com.ort.isp.cryptoapp.data.model.out.LoginCredential

interface RemoteLoginDataSource {
    suspend fun login(credential: LoginCredential) : Resource<LoggedInUser>
}