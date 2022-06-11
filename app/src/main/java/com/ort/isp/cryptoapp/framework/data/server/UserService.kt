package com.ort.isp.cryptoapp.framework.data.server

import com.ort.isp.cryptoapp.data.model.`in`.UserFullData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users/{userId}")
    suspend fun getUserFullData(@Path("userId") userId: String): Response<UserFullData>
}