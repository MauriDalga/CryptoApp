package com.ort.isp.cryptoapp.framework

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ort.isp.cryptoapp.data.repository.LoginRepository
import com.ort.isp.cryptoapp.data.source.*
import com.ort.isp.cryptoapp.framework.data.local.LoginSharedPreferenceDataSource
import com.ort.isp.cryptoapp.framework.data.server.*
import com.ort.isp.cryptoapp.framework.tools.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FrameworkModule {

    @Singleton
    @Provides
    @Named(RETROFIT_WITHOUT_AUTH)
    fun retrofitWithoutAuthProvider(): Retrofit {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder().baseUrl(BASE_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun retrofitAuthInterceptorProvider(loginRepository: LoginRepository) =
        AuthInterceptor(loginRepository)

    @Singleton
    @Provides
    @Named(RETROFIT_WITH_AUTH)
    fun retrofitWithAuthProvider(authInterceptor: AuthInterceptor): Retrofit {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .build()
        return Retrofit.Builder().baseUrl(BASE_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun loginServiceProvider(@Named(RETROFIT_WITHOUT_AUTH) retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun loginServerDataSource(loginService: LoginService): RemoteLoginDataSource =
        LoginServerDataSource(loginService)

    @Singleton
    @Provides
    fun transactionServiceProvider(@Named(RETROFIT_WITH_AUTH) retrofit: Retrofit): TransactionService =
        retrofit.create(TransactionService::class.java)

    @Singleton
    @Provides
    fun userServiceProvider(@Named(RETROFIT_WITH_AUTH) retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun loginSharedPreferencesDataSource(sharedPreferences: SharedPreferences): LocalSessionDataSource =
        LoginSharedPreferenceDataSource(sharedPreferences)

    @Singleton
    @Provides
    fun sharedPreferencesProvider(application: Application): SharedPreferences =
        application.getSharedPreferences("userPreferences", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun registerServiceProvider(@Named(RETROFIT_WITHOUT_AUTH) retrofit: Retrofit): RegisterService =
        retrofit.create(RegisterService::class.java)

    @Singleton
    @Provides
    fun registerServerDataSource(registerService: RegisterService): RemoteRegisterDataSource =
        RegisterServerDataSource(registerService)

    @Singleton
    @Provides
    fun transactionServerDataSourceProvider(transactionService: TransactionService): RemoteTransactionDataSource =
        TransactionServerDataSource(transactionService)

    @Singleton
    @Provides
    fun userServerDataSourceProvider(userService: UserService): RemoteUserDataSource =
        UserServerDataSource(userService)
}

const val RETROFIT_WITHOUT_AUTH = "RetrofitWithoutAuth"
const val RETROFIT_WITH_AUTH = "RetrofitWithAuth"
const val BASE_API_URL = "http://10.0.2.2:5165/api/"