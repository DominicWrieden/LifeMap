package com.dominicwrieden.lifemap.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Observer
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

fun <T>Observable<T>.toLiveData() =
        LiveDataReactiveStreams.fromPublisher(toFlowable(BackpressureStrategy.LATEST))

fun <T> LiveData<T>.observeWith(owner: LifecycleOwner, observer: (T)->Unit) =
        observe(owner, Observer { observer(it!!) })
