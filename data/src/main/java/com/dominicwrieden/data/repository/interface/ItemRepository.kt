package com.dominicwrieden.data.repository.`interface`

import com.dominicwrieden.api.model.Item
import com.dominicwrieden.api.model.ItemEntry
import com.dominicwrieden.api.model.Property
import com.dominicwrieden.api.model.Response
import io.reactivex.Single

interface ItemRepository {

    fun saveItem(item: Item)

    fun saveItems(items: List<Item>)

    fun saveItemEntry(itemEntry: ItemEntry)

    fun saveItemEntries(itemEntries: List<ItemEntry>)

    fun getItems(areaId:String): Single<Response<List<Item>>>

    fun saveProperty(property: Property<Any>)

    fun saveProperties(properties: List<Property<Any>>)
}