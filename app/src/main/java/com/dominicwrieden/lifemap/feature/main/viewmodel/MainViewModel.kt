package com.dominicwrieden.lifemap.feature.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dominicwrieden.api.model.AuthenticationStatus
import com.dominicwrieden.data.repository.authentication.AuthenticationRepository
import com.dominicwrieden.lifemap.core.BaseViewModel
import com.dominicwrieden.lifemap.feature.main.model.StartScreen
import com.dominicwrieden.lifemap.util.Event
import com.dominicwrieden.lifemap.util.toLiveData

class MainViewModel(private val authenticationRepository: AuthenticationRepository) : BaseViewModel() {


    val startScreen: LiveData<Event<StartScreen>> = MutableLiveData(
        authenticationRepository.authenticationStatus
            .map { status ->
                Event(
                    when (status) {
                        is AuthenticationStatus.LoggedIn -> StartScreen.Map
                        AuthenticationStatus.LoggedOut -> StartScreen.Login
                    }
                )
            }.blockingFirst(Event(StartScreen.Login))
    )

    val logout: LiveData<Event<Unit>> = authenticationRepository.authenticationStatus
        .filter { it is AuthenticationStatus.LoggedOut }
        .map { Event(Unit) }
        .toLiveData()

}