package com.dominicwrieden.data.repository.`interface`

import com.dominicwrieden.api.model.ItemType
import com.dominicwrieden.api.model.Response
import io.reactivex.Single

interface ItemTypeRepository {

    fun saveItemType(itemType: ItemType)

    fun saveItemTypes(itemTypes: List<ItemType>)

    fun getItemTypes(): Single<Response<List<ItemType>>>
}