package com.dominicwrieden.lifemap.usecase.state

import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.state.StateRepository
import io.reactivex.rxjava3.core.Single

interface DownloadStatesUseCase {
    operator fun invoke(): Single<Task>
}

class DownloadStatesUseCaseImpl(private val statesRepository: StateRepository) :
    DownloadStatesUseCase {
    override fun invoke() = statesRepository.downloadStates()
}