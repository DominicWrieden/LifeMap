package com.dominicwrieden.lifemap.usecase.authentication

import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.authentication.AuthenticationRepository
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject


interface LoginUseCase {
    operator fun invoke(userName: String, password: String): Single<Task>
}

class LoginUseCaseImpl : LoginUseCase {

    val authenticationRepository:AuthenticationRepository by  inject(AuthenticationRepository::class.java)

    override fun invoke(userName: String, password: String) =
        authenticationRepository.login(userName, password)

}