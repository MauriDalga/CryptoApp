package com.ort.isp.cryptoapp.framework.data.server

import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.LoggedInUser
import com.ort.isp.cryptoapp.data.model.`in`.UserFullData
import com.ort.isp.cryptoapp.data.source.RemoteUserDataSource
import javax.inject.Inject

class UserServerDataSource @Inject constructor(private val userService: UserService) :
    AbstractServerDataSource(), RemoteUserDataSource {
    override suspend fun getUserFullData(loggedInUser: LoggedInUser): Resource<UserFullData> {
        return safeApiCall { userService.getUserFullData(loggedInUser.id) }
    }
}