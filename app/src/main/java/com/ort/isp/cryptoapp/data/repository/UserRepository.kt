package com.ort.isp.cryptoapp.data.repository

import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.UserFullData
import com.ort.isp.cryptoapp.data.model.out.UpdatedUser
import com.ort.isp.cryptoapp.data.source.LocalSessionDataSource
import com.ort.isp.cryptoapp.data.source.RemoteUserDataSource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteCoinAccountDataSource: RemoteUserDataSource,
    private val localSessionDataSource: LocalSessionDataSource
) {
    suspend fun getUserFullData(): Resource<UserFullData> {
        val userSession = localSessionDataSource.get()!!
        return remoteCoinAccountDataSource.getUserFullData(userSession)
    }

    suspend fun uploadUserProfilePhoto(photo: String): Resource<Unit> {
        val userSession = localSessionDataSource.get()!!
        val updatedUser = UpdatedUser.createFrom(userSession, photo)
        return remoteCoinAccountDataSource.uploadUserProfilePhoto(userSession.id, updatedUser)
    }
}