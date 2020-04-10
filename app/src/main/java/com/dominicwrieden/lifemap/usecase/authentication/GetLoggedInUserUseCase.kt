package com.dominicwrieden.lifemap.usecase.authentication

import com.dominicwrieden.api.model.User
import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.repository.authentication.AuthenticationRepository
import io.reactivex.Single

interface GetLoggedInUserUseCase {
    operator fun invoke(): Single<Result<User>>
}

class GetLoggedInUserUseCaseImpl(private val authenticationRepository: AuthenticationRepository) :
    GetLoggedInUserUseCase {
    override fun invoke() = authenticationRepository.getLoggedInUser()

}