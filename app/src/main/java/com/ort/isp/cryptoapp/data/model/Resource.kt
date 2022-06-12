package com.ort.isp.cryptoapp.data.model

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data = data)

    class Error<T>(errorMessage: String) : Resource<T>(message = errorMessage)

    class Unauthorized<T> : Resource<T>()

    class Loading<T> : Resource<T>()
}