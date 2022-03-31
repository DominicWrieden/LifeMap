package com.dominicwrieden.lifemap.usecase.propertyconfig

import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.propertyconfig.PropertyConfigRepository
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject

interface DownloadPropertyConfigsUseCase {
    operator fun invoke(): Single<Task>
}

class DownloadPropertyConfigsUseCaseImpl : DownloadPropertyConfigsUseCase {

    val propertyConfigRepository: PropertyConfigRepository by inject(PropertyConfigRepository::class.java)

    override fun invoke() = propertyConfigRepository.downloadPropertyConfigs()
}