package com.dominicwrieden.lifemap.feature.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    val userName :LiveData<String> = MutableLiveData("Test")
    val password :LiveData<String> = MutableLiveData("12345")

    fun onLoginClicked() {

    }
}