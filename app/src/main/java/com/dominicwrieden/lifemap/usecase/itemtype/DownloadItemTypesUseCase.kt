package com.dominicwrieden.lifemap.usecase.itemtype

import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.itemtype.ItemTypeRepository
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject

interface DownloadItemTypesUseCase {
    operator fun invoke(): Single<Task>
}

class DownloadItemTypesUseCaseImpl : DownloadItemTypesUseCase {

    val itemTypeRepository: ItemTypeRepository by inject(ItemTypeRepository::class.java)

    override fun invoke() = itemTypeRepository.downloadItemTypes()
}