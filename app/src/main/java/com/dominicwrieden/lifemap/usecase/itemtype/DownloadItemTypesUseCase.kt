package com.dominicwrieden.lifemap.usecase.itemtype

import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.itemtype.ItemTypeRepository
import io.reactivex.Single

interface DownloadItemTypesUseCase {
    operator fun invoke(): Single<Task>
}

class DownloadItemTypesUseCaseImpl(private val itemTypeRepository: ItemTypeRepository) :
    DownloadItemTypesUseCase {
    override fun invoke() = itemTypeRepository.downloadItemTypes()
}