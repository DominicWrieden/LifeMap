package com.dominicwrieden.data.repository.implementation

import com.dominicwrieden.LifeMapDatabaseQueries
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.model.*
import com.dominicwrieden.data.repository.`interface`.ItemRepository
import org.threeten.bp.OffsetDateTime

class ItemRepositoryImpl(private val database: LifeMapDatabaseQueries, private val api: Api) : ItemRepository {

    override fun getItems(areaId:String) = api.getItems(areaId)

    override fun saveItem(item: Item) {
        saveItems(listOf(item))
    }

    override fun saveItems(items: List<Item>) {

        val updatedItems = arrayListOf<Item>()
        database.transaction {
            items.forEach { item ->
                database.insertItem(
                    localIdItem = item.localId,
                    remoteIdItem = item.remoteId,
                    remoteIdArea = item.areaId,
                    createDate = item.createDate,
                    idReporter = item.reporterId
                )

                //insert into the item the localId, needed to insert the item entries afterwards
                updatedItems.add(
                    item.copy(
                        localId = database.getAutoIncrementedIdFromLastInsert().executeAsOne()
                    )
                )
            }
        }

        //merge all item entries to on list
        val allItemEntries = updatedItems.map { it.history }.flatten()

        saveItemEntries(allItemEntries)
    }

    override fun saveItemEntry(itemEntry: ItemEntry) {
        saveItemEntries(listOf(itemEntry))
    }

    override fun saveItemEntries(itemEntries: List<ItemEntry>) {

        val updatedProperties = arrayListOf<Property<Any>>()

        database.transaction {
            itemEntries.forEach { itemEntry ->
                database.insertItemEntry(
                    localIdItemEntry = itemEntry.localId,
                    remoteIdItemEntry = itemEntry.remoteId,
                    localIdItem = itemEntry.itemId,
                    idReporter = itemEntry.reporterId,
                    createDate = itemEntry.reportDate,
                    idItemType = itemEntry.itemTypeId,
                    location = itemEntry.location,
                    idState = itemEntry.stateId,
                    note = itemEntry.note
                )
                updatedProperties.addAll(itemEntry.properties.map { property ->
                    when (property.value) {
                        is Int -> PropertyInt(
                            localId = property.localId,
                            itemEntryId = database.getAutoIncrementedIdFromLastInsert().executeAsOne(),
                            propertyConfigId = property.propertyConfigId,
                            value = property.value as Int
                        )
                        is OffsetDateTime -> PropertyDate(
                            localId = property.localId,
                            itemEntryId = database.getAutoIncrementedIdFromLastInsert().executeAsOne(),
                            propertyConfigId = property.propertyConfigId,
                            value = property.value as OffsetDateTime
                        )
                        is String -> PropertyString(
                            localId = property.localId,
                            itemEntryId = database.getAutoIncrementedIdFromLastInsert().executeAsOne(),
                            propertyConfigId = property.propertyConfigId,
                            value = property.value as String
                        )
                        else -> null
                    } as Property<Any>
                }
                )
            }
        }

        saveProperties(updatedProperties)

    }

    override fun saveProperty(property: Property<Any>) {
        saveProperties(listOf(property))
    }

    override fun saveProperties(properties: List<Property<Any>>) {
        database.transaction {
            properties.forEach { property ->
                database.insertProperty(
                    localIdProperty = property.localId,
                    localIdItemEntry = property.itemEntryId!!,
                    propertyConfigId = property.propertyConfigId,
                    value =  property.value.toString() // TODO Adapter schreiben, um Date richtig in einen String umzuwandeln
                )
            }
        }
    }

}