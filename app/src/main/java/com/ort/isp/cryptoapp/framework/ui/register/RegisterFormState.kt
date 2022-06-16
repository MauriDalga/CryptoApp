package com.ort.isp.cryptoapp.framework.ui.register

/**
 * Data validation state of the login form.
 */
class RegisterFormState(
    val nameError: Int? = null,
    val lastnameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val secondPasswordError: Int? = null,
    val isDataValid: Boolean = false
)