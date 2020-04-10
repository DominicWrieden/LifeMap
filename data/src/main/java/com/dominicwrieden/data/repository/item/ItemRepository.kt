package com.dominicwrieden.data.repository.item

import com.dominicwrieden.data.model.Task
import io.reactivex.Single

interface ItemRepository {

    fun downloadItemsForArea(areaId: String): Single<Task>
}