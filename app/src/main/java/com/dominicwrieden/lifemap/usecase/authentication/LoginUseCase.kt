package com.dominicwrieden.lifemap.usecase.authentication

import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.authentication.AuthenticationRepository
import io.reactivex.rxjava3.core.Single


interface LoginUseCase {
    operator fun invoke(userName: String, password: String): Single<Task>
}

class LoginUseCaseImpl(private val authenticationRepository: AuthenticationRepository) :
    LoginUseCase {
    override fun invoke(userName: String, password: String) =
        authenticationRepository.login(userName, password)

}