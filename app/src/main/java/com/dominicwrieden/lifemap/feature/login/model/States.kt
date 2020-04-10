package com.dominicwrieden.lifemap.feature.login.model

import androidx.annotation.StringRes

sealed class LoginButtonState {
    object Initial : LoginButtonState()
    object Loading : LoginButtonState()
}

sealed class LoginTextInputState {
    object Initial : LoginTextInputState()
    object Loading : LoginTextInputState()
    data class Error(@StringRes val errorMessage: Int) : LoginTextInputState()
}

sealed class LoginMessageState {
    object Initial : LoginMessageState()
    data class Loading(@StringRes val progressMessage: Int) : LoginMessageState()
    data class Error(@StringRes val errorMessage: Int) : LoginMessageState()
}