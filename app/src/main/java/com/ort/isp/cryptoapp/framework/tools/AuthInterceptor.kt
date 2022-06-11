package com.ort.isp.cryptoapp.framework.tools

import com.ort.isp.cryptoapp.data.repository.LoginRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor to add auth token to requests
 */
class AuthInterceptor @Inject constructor(private val loginRepository: LoginRepository) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        loginRepository.getSession()?.let {
            requestBuilder.addHeader("Authorization", it.token)
        }

        return chain.proceed(requestBuilder.build())
    }
}