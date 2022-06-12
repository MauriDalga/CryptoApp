package com.ort.isp.cryptoapp.framework.data.server

import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.framework.data.model.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

abstract class AbstractServerDataSource {

    /**
     * Use this function in all retrofit calls to handle api errors.
     */
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()

                when {
                    response.isSuccessful -> {
                        response.body()?.let { Resource.Success(data = response.body()) }
                            ?: Resource.Success(null)
                    }
                    response.code() == HTTP_UNAUTHORIZED_CODE -> {
                        Resource.Unauthorized()
                    }
                    else -> {
                        Resource.Error(
                            errorMessage = convertErrorBody(response.errorBody())?.message
                                ?: SOMETHING_WENT_WRONG
                        )
                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is HttpException, is ConnectException -> Resource.Error(
                        errorMessage = CHECK_YOUR_INTERNET
                    )
                    else -> Resource.Error(errorMessage = SOMETHING_WENT_WRONG)
                }
            }
        }
    }

    private fun convertErrorBody(errorBody: ResponseBody?): ErrorResponse? {
        return try {
            return ErrorResponse(errorBody?.string() ?: SOMETHING_WENT_WRONG)
        } catch (exception: Exception) {
            ErrorResponse(SOMETHING_WENT_WRONG)
        }
    }
}

private const val SOMETHING_WENT_WRONG = "Error desconocido"
private const val CHECK_YOUR_INTERNET = "Por favor, revisa tu conexión a internet"
private const val HTTP_UNAUTHORIZED_CODE = 401