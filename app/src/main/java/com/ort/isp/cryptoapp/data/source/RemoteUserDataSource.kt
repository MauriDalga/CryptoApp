package com.ort.isp.cryptoapp.data.source

import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.LoggedInUser
import com.ort.isp.cryptoapp.data.model.`in`.UserFullData

interface RemoteUserDataSource {
    suspend fun getUserFullData(loggedInUser: LoggedInUser): Resource<UserFullData>
}