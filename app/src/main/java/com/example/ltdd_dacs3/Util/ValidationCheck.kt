package com.example.ltdd_dacs3.Util

import android.util.Patterns


fun validateEmail(email: String ): RegisterValidation{
    if (email.isEmpty()){
        return RegisterValidation.Failed("Email can't be empty")
    }

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        return RegisterValidation.Failed("Wrong email format ")
    }

    return RegisterValidation.Success
}

fun validatePassword(password: String): RegisterValidation{
    if (password.isEmpty()){
        return RegisterValidation.Failed("Password can't be empty")
    }

    if (password.length < 6 ){
        return RegisterValidation.Failed("Password should contains 6 char")
    }

    val containsLetter = password.any { it.isLetter() }
    val containsDigit = password.any { it.isDigit() }

    if (!containsLetter || !containsDigit) {
        return RegisterValidation.Failed("Password must contain both letters and digits")
    }

    return RegisterValidation.Success
}

fun validateConfirmPassword(password: String, confirmPassword: String): RegisterValidation {
    val passwordValidation = validatePassword(password)
    if (passwordValidation is RegisterValidation.Failed) {
        return passwordValidation
    }

    if (password != confirmPassword) {
        return RegisterValidation.Failed("Passwords do not match")
    }

    return RegisterValidation.Success
}