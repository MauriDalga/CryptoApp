package com.ort.isp.cryptoapp.framework

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ort.isp.cryptoapp.data.source.LocalSessionDataSource
import com.ort.isp.cryptoapp.data.source.RemoteLoginDataSource
import com.ort.isp.cryptoapp.data.source.RemoteRegisterDataSource
import com.ort.isp.cryptoapp.framework.data.local.LoginSharedPreferenceDataSource
import com.ort.isp.cryptoapp.framework.data.server.LoginServerDataSource
import com.ort.isp.cryptoapp.framework.data.server.LoginService
import com.ort.isp.cryptoapp.framework.data.server.RegisterServerDataSource
import com.ort.isp.cryptoapp.framework.data.server.RegisterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class FrameworkModule {

    @Singleton
    @Provides
    fun retrofitProvider(): Retrofit {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder().baseUrl("https://localhost")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun loginServiceProvider(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun loginServerDataSource(loginService: LoginService): RemoteLoginDataSource =
        LoginServerDataSource(loginService)

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
    fun registerServiceProvider(retrofit: Retrofit): RegisterService =
        retrofit.create(RegisterService::class.java)

    @Singleton
    @Provides
    fun registerServerDataSource(registerService: RegisterService): RemoteRegisterDataSource =
        RegisterServerDataSource(registerService)


}