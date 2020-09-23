package com.dominicwrieden.data.repository.item

import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.model.Task
import com.dominicwrieden.data.repository.item.model.AreaItem
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface ItemRepository {

    fun downloadItemsForArea(areaId: String): Single<Task>

    fun getItemsForArea(areaId: String) : Observable<Result<List<AreaItem>>>
}