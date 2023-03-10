package com.ort.isp.cryptoapp.data.source

import com.ort.isp.cryptoapp.data.model.LoggedUserLocalData
import com.ort.isp.cryptoapp.data.model.`in`.LoggedInUser

interface LocalSessionDataSource {
    fun save(loggedInUser: LoggedInUser): LoggedUserLocalData
    fun get(): LoggedUserLocalData?
    fun delete()
}