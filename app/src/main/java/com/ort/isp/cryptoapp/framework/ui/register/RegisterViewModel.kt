package com.ort.isp.cryptoapp.framework.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.RegisteredUser
import com.ort.isp.cryptoapp.data.model.out.RegisterCredential
import com.ort.isp.cryptoapp.data.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerRepository: RegisterRepository) :
    ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<Resource<RegisteredUser>>()
    val registerResult: LiveData<Resource<RegisteredUser>> = _registerResult

    fun register(name: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            _registerResult.value = Resource.Loading()
            _registerResult.value = registerRepository.register(RegisterCredential(name, lastName, email, password))
        }
    }

    fun registerDataChanged(name: String, lastName: String, email: String, password: String, secondPassword: String) {
        if (name.isEmpty()) {
            _registerForm.value = RegisterFormState(nameError = R.string.invalid_name)
        } else if (lastName.isEmpty()) {
            _registerForm.value = RegisterFormState(lastNameError = R.string.invalid_last_name)
        } else if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else if (!isPasswordValid(secondPassword)) {
            _registerForm.value = RegisterFormState(secondPasswordError = R.string.invalid_password)
        } else if (!arePasswordsTheSame(password, secondPassword)) {
            _registerForm.value = RegisterFormState(secondPasswordError = R.string.invalid_password_combination)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    fun isFormDataValid(): Boolean {
        return _registerForm.value?.isDataValid ?: false
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= PASSWORD_MIN_LENGTH
    }

    private fun arePasswordsTheSame(password: String, secondPassword: String): Boolean {
        return password.equals(secondPassword)
    }
}

private const val PASSWORD_MIN_LENGTH = 7