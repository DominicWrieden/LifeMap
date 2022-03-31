package com.dominicwrieden.lifemap.usecase.authentication

import com.dominicwrieden.api.model.User
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.repository.authentication.AuthenticationRepository
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject

interface GetLoggedInUserUseCase {
    operator fun invoke(): Single<Result<User>>
}

class GetLoggedInUserUseCaseImpl : GetLoggedInUserUseCase {

    val authenticationRepository: AuthenticationRepository by inject(AuthenticationRepository::class.java)

    override fun invoke() = authenticationRepository.getLoggedInUser()

}