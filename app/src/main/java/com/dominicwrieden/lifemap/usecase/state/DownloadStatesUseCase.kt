package com.dominicwrieden.lifemap.usecase.state

import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.state.StateRepository
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject

interface DownloadStatesUseCase {
    operator fun invoke(): Single<Task>
}

class DownloadStatesUseCaseImpl : DownloadStatesUseCase {

    val statesRepository: StateRepository by inject(StateRepository::class.java)

    override fun invoke() = statesRepository.downloadStates()
}