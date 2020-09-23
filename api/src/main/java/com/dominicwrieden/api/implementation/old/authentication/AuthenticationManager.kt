package com.dominicwrieden.api.implementation.old.authentication

import com.dominicwrieden.api.implementation.old.authentication.source.local.AuthenticationSharedPreferences
import com.dominicwrieden.api.model.AuthenticationStatus
import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.core.Observable

internal class AuthenticationManager(private val authenticationSharedPreferences: AuthenticationSharedPreferences) {

    private val authenticationRelay = BehaviorRelay.create<AuthenticationStatus>()

    val authenticationStatus: Observable<AuthenticationStatus> = authenticationRelay.hide()

    init {
        if (authenticationSharedPreferences.getUserName().isNotBlank()
            && authenticationSharedPreferences.getFirstName().isNotBlank()
            && authenticationSharedPreferences.getLastName().isNotBlank()
            && authenticationSharedPreferences.getPassword().isNotBlank()
            && authenticationSharedPreferences.getToken().isNotBlank()
        ) {
            authenticationRelay.accept(
                AuthenticationStatus.LoggedIn(
                    firstName = authenticationSharedPreferences.getFirstName(),
                    lastName = authenticationSharedPreferences.getLastName(),
                    username = authenticationSharedPreferences.getUserName()
                )
            )
        } else {
            logout()
        }
    }

    fun login(username: String, firstName: String, lastName:String, password:String, token:String) {
        authenticationSharedPreferences.saveUserName(username)
        authenticationSharedPreferences.saveFirstName(firstName)
        authenticationSharedPreferences.saveLastName(lastName)
        authenticationSharedPreferences.savePassword(password)
        authenticationSharedPreferences.saveToken(token)

        authenticationRelay.accept(AuthenticationStatus.LoggedIn(firstName, lastName, username))
    }

    fun logout() {
        clearUser()
        authenticationRelay.accept(AuthenticationStatus.LoggedOut)
    }

    private fun clearUser() {
        authenticationSharedPreferences.clearUserName()
        authenticationSharedPreferences.clearFirstName()
        authenticationSharedPreferences.clearLastName()
        authenticationSharedPreferences.clearPassword()
        authenticationSharedPreferences.clearToken()
    }
}