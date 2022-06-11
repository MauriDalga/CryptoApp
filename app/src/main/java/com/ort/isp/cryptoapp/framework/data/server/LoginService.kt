package com.ort.isp.cryptoapp.framework.data.server

import com.ort.isp.cryptoapp.data.model.`in`.LoggedInUser
import com.ort.isp.cryptoapp.data.model.out.LoginCredential
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend fun login(@Body loginCredential: LoginCredential): Response<LoggedInUser>
}