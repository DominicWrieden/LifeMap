package com.dominicwrieden.lifemap.usecase.user

import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.user.UserRepository
import io.reactivex.rxjava3.core.Single

interface DownloadUsersUseCase {
    operator fun invoke(): Single<Task>
}

class DownloadUsersUseCaseImpl(private val userRepository: UserRepository) :
    DownloadUsersUseCase {
    override fun invoke() = userRepository.downloadUsers()
}