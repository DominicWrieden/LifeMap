package com.dominicwrieden.lifemap.usecase

import com.dominicwrieden.api.model.Response
import com.dominicwrieden.data.repository.`interface`.*

class DownloadUseCase(
    private val userRepository: UserRepository,
    private val areaRepository: AreaRepository,
    private val itemTypeRepository: ItemTypeRepository,
    private val propertyConfigRepository: PropertyConfigRepository,
    private val stateRepository: StateRepository,
    private val itemRepository: ItemRepository

) {

    fun getAreas() = areaRepository.getAreas()
        .doOnSuccess { areaResponse ->
            if (areaResponse is Response.Success) {
                areaRepository.saveAreas(areaResponse.body)
            }
        }

    fun getItemTypes() = itemTypeRepository.getItemTypes()
        .doOnSuccess { itemTypeResponse ->
            if (itemTypeResponse is Response.Success) {
                itemTypeRepository.saveItemTypes(itemTypeResponse.body)
            }
        }

    fun getUsers() = userRepository.getUsers()
        .doOnSuccess { userResponse ->
            if (userResponse is Response.Success) {
                userRepository.saveUsers(userResponse.body)
            }
        }

    fun getStates() = stateRepository.getStates()
        .doOnSuccess { stateResponse ->
            if (stateResponse is Response.Success) {
                stateRepository.saveStates(stateResponse.body)
            }
        }

    fun getItems(areaId: String) = itemRepository.getItems(areaId)
        .doOnSuccess { itemResponse ->
            if (itemResponse is Response.Success) {
                itemRepository.saveItems(itemResponse.body)
            }
        }

    fun getPropertyConfigs() = propertyConfigRepository.getPropertyConfigs()
        .doOnSuccess { propertyConfigResponse ->
            if (propertyConfigResponse is Response.Success) {
                propertyConfigRepository.savePropertyConfigs(propertyConfigResponse.body)
            }
        }
}