package com.dominicwrieden.lifemap.usecase.user

import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.user.UserRepository
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject

interface DownloadUsersUseCase {
    operator fun invoke(): Single<Task>
}

class DownloadUsersUseCaseImpl : DownloadUsersUseCase {

    val userRepository: UserRepository by inject(UserRepository::class.java)

    override fun invoke() = userRepository.downloadUsers()
}