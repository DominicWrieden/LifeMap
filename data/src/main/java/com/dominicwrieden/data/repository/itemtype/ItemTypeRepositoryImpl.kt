package com.dominicwrieden.data.repository.itemtype

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.ItemType
import com.dominicwrieden.api.model.Response
import com.dominicwrieden.data.model.toTask
import org.koin.java.KoinJavaComponent.inject

class ItemTypeRepositoryImpl: ItemTypeRepository {

    val database: LifeMapDatabaseQueries by inject(LifeMapDatabaseQueries::class.java)
    val api: Api by inject(Api::class.java)

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