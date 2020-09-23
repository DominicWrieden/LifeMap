package com.dominicwrieden.data.repository.itemtype

import com.dominicwrieden.data.model.Task
import io.reactivex.rxjava3.core.Single

interface ItemTypeRepository {

    fun downloadItemTypes(): Single<Task>
}