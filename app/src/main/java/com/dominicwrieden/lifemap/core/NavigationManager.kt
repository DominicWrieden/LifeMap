package com.dominicwrieden.lifemap.core

import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable

class NavigationManager {

    private val navigationStateRelay = BehaviorRelay.create<Destination>()

    val destination: Observable<Destination> = navigationStateRelay.hide()

    fun setNextDestination(destination: Destination) {
        navigationStateRelay.accept(destination)
    }
}

sealed class Destination {
    data class NavigationDirection(val navigationDirection: NavDirections): Destination()
    data class Action(@IdRes val actionId: Int): Destination()
}