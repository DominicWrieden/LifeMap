package com.dominicwrieden.lifemap.feature.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    val text:LiveData<String> = MutableLiveData("Test")
}