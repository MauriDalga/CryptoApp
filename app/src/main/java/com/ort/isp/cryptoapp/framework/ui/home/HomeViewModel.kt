package com.ort.isp.cryptoapp.framework.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.UserFullData
import com.ort.isp.cryptoapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _userFullData = MutableLiveData<Resource<UserFullData>>()
    val userFullData: LiveData<Resource<UserFullData>> = _userFullData

    fun fetchUserData() {
        viewModelScope.launch {
            _userFullData.value = Resource.Loading()
            _userFullData.value = userRepository.getUserFullData()
        }
    }
}