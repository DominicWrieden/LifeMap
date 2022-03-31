package com.dominicwrieden.lifemap.usecase.item

import com.dominicwrieden.data.model.Result
import com.dominicwrieden.data.repository.item.ItemRepository
import com.dominicwrieden.data.repository.item.model.AreaItem
import io.reactivex.rxjava3.core.Observable
import org.koin.java.KoinJavaComponent.inject

interface GetItemsForAreaUseCase {
    operator fun invoke(areaId: String): Observable<Result<List<AreaItem>>>
}

class GetItemsForAreaUseCaseImpl: GetItemsForAreaUseCase {

    val itemRepository: ItemRepository by inject(ItemRepository::class.java)

    override fun invoke(areaId: String): Observable<Result<List<AreaItem>>> =
        itemRepository.getItemsForArea(areaId)

}
