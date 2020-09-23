package com.dominicwrieden.lifemap.usecase.propertyconfig

import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.propertyconfig.PropertyConfigRepository
import io.reactivex.rxjava3.core.Single

interface DownloadPropertyConfigsUseCase {
    operator fun invoke(): Single<Task>
}

class DownloadPropertyConfigsUseCaseImpl(private val propertyConfigRepository: PropertyConfigRepository) :
    DownloadPropertyConfigsUseCase {
    override fun invoke() = propertyConfigRepository.downloadPropertyConfigs()
}