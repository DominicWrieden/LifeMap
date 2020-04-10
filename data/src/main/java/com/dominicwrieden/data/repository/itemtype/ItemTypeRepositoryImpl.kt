package com.dominicwrieden.data.repository.itemtype

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.ItemType
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.data.model.toTask

class ItemTypeRepositoryImpl(private val database: LifeMapDatabaseQueries, private val api: Api):
    ItemTypeRepository {

    override fun downloadItemTypes() = api.getItemTypes()
        .doOnSuccess { itemTypesResponse ->
            itemTypesResponse?.let {
                when (it) {
                    is Response.Success -> saveItemTypes(it.body)
                }
            }
        }
        .toTask()

    private fun saveItemType(itemType: ItemType) {
        saveItemTypes(listOf(itemType))
    }

    private fun saveItemTypes(itemTypes: List<ItemType>) {
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