package com.dominicwrieden.lifemap.feature.main.viewmodel

import androidx.lifecycle.LiveData
import com.dominicwrieden.api.model.AuthenticationStatus
import com.dominicwrieden.data.repository.authentication.AuthenticationRepository
import com.dominicwrieden.lifemap.R
import com.dominicwrieden.lifemap.core.BaseViewModel
import com.dominicwrieden.lifemap.core.Destination
import com.dominicwrieden.lifemap.core.NavigationManager
import com.dominicwrieden.lifemap.util.Event
import com.dominicwrieden.lifemap.util.toUnsubscribedEventLiveData

class MainViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val navigationManager: NavigationManager
) : BaseViewModel() {

    val startDestination: LiveData<Event<Int>> = authenticationRepository.authenticationStatus
        .firstOrError()
        .map { authenticationStatus ->
            when (authenticationStatus) {
                is AuthenticationStatus.LoggedIn -> R.id.mapFragment
                else -> R.id.loginComposeFragment
            }
        }.onErrorReturn {
            //TODO do logout
            R.id.loginButton
        }
        .toUnsubscribedEventLiveData(disposable)


    val destination: LiveData<Event<Destination>> =
        navigationManager.destination.toUnsubscribedEventLiveData(disposable)
}