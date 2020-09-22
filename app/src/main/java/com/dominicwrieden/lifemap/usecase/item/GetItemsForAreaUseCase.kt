package com.dominicwrieden.lifemap.usecase.item

import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.repository.item.ItemRepository
import com.dominicwrieden.data.repository.item.model.AreaItem
import io.reactivex.Observable

interface GetItemsForAreaUseCase {
    operator fun invoke(areaId: String): Observable<Result<List<AreaItem>>>
}

class GetItemsForAreaUseCaseImpl(
    private val itemRepository: ItemRepository
) :
    GetItemsForAreaUseCase {
    override fun invoke(areaId: String): Observable<Result<List<AreaItem>>> =
        itemRepository.getItemsForArea(areaId)

}
