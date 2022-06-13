package com.ort.isp.cryptoapp.data.source

import com.ort.isp.cryptoapp.data.model.LoggedUserLocalData
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.UserFullData
import com.ort.isp.cryptoapp.data.model.out.UpdatedUser

interface RemoteUserDataSource {
    suspend fun getUserFullData(loggedUserLocalData: LoggedUserLocalData): Resource<UserFullData>
    suspend fun uploadUserProfilePhoto(userId: String, updatedUser: UpdatedUser): Resource<Unit>
}