package com.ort.isp.cryptoapp.framework.data.server

import com.ort.isp.cryptoapp.data.model.`in`.RegisteredUser
import com.ort.isp.cryptoapp.data.model.out.RegisterCredential
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("users")
    suspend fun register(@Body registerCredential: RegisterCredential): Response<RegisteredUser>
}