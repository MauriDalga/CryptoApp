package com.ort.isp.cryptoapp.framework.ui.home

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.UserFullData
import com.ort.isp.cryptoapp.data.repository.UserRepository
import com.ort.isp.cryptoapp.framework.ui.shared.toBase64String
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _userFullData = MutableLiveData<Resource<UserFullData>>()
    val userFullData: LiveData<Resource<UserFullData>> = _userFullData

    private val _userPhotoUploadStatus = MutableLiveData<Resource<Bitmap>>()
    val userPhotoUploadStatus: LiveData<Resource<Bitmap>> = _userPhotoUploadStatus

    fun fetchUserData() {
        viewModelScope.launch {
            _userFullData.value = Resource.Loading()
            _userFullData.value = userRepository.getUserFullData()
        }
    }

    fun uploadUserPhoto(photo: Bitmap) {
        viewModelScope.launch {
            when (val userUploadResult =
                userRepository.uploadUserProfilePhoto(photo.toBase64String())) {
                is Resource.Loading -> {
                    // Do nothing
                }
                is Resource.Success -> {
                    _userPhotoUploadStatus.value = Resource.Success(photo)
                }
                is Resource.Unauthorized -> {
                    _userPhotoUploadStatus.value = Resource.Unauthorized()
                }
                else -> {
                    _userPhotoUploadStatus.value = Resource.Error(userUploadResult.message!!)
                }
            }
        }
    }
}