package com.dominicwrieden.lifemap.usecase

import com.dominicwrieden.data.repository.`interface`.AuthenticationRepository

class LoginUseCase(private val authenticationRepository: AuthenticationRepository) {

    fun login(username: String, password:String) = authenticationRepository.login(username,password)
}