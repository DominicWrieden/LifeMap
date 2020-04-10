package com.dominicwrieden.data.repository.itemtype

import com.dominicwrieden.data.model.Task
import io.reactivex.Single

interface ItemTypeRepository {

    fun downloadItemTypes(): Single<Task>
}