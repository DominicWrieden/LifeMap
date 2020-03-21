package com.dominicwrieden.data.repository.implementation

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.ItemType
import com.dominicwrieden.data.repository.`interface`.ItemTypeRepository

class ItemTypeRepositoryImpl(private val database: LifeMapDatabaseQueries, private val api: Api):ItemTypeRepository {

    override fun getItemTypes() = api.getItemTypes()

    override fun saveItemType(itemType: ItemType) {
        saveItemTypes(listOf(itemType))
    }

    override fun saveItemTypes(itemTypes: List<ItemType>) {
        database.transaction {
            itemTypes.forEach {itemType ->
                database.insertItemType(
                    remoteIdItemType = itemType.remoteId,
                    name = itemType.name,
                    description = itemType.description
                )
            }
        }
    }
}