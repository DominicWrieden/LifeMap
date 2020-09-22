package com.dominicwrieden.lifemap.util

import androidx.lifecycle.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

fun <T>Observable<T>.toLiveData() =
        LiveDataReactiveStreams.fromPublisher(toFlowable(BackpressureStrategy.LATEST))

/**
 * If the normal .toLiveData() function is used, and the view observes on the live data,
 * then putting the app into the background and putting it back into the foreground, it will letting emit the last item again.
 *
 * This method will prohibit this behaviour, and will only emit the last item, if the view renews
 * it's observation on the live data e.g. caused by a configuration change
 */
fun <T>Observable<T>.toUnsubscribedLiveData(disposable: CompositeDisposable):LiveData<T> {
        val mutableLiveData = MutableLiveData<T>()

        this.subscribe { mutableLiveData.postValue(it)  }.addTo(disposable)

        return mutableLiveData
}

/**
 * See doc from {@link #toUnsubscribedLiveData}
 */
fun <T>Observable<T>.toUnsubscribedEventLiveData(disposable: CompositeDisposable):LiveData<Event<T>> {
        val mutableLiveData = MutableLiveData<Event<T>>()

        this.subscribe { mutableLiveData.postValue(Event(it))  }.addTo(disposable)

        return mutableLiveData
}


/**
 * See doc from {@link #toUnsubscribedLiveData}
 */
fun <T> Single<T>.toUnsubscribedEventLiveData(disposable: CompositeDisposable): LiveData<Event<T>> {
        val mutableLiveData = MutableLiveData<Event<T>>()

        this.map { Event(it) }.subscribe (mutableLiveData::postValue).addTo(disposable)

        return mutableLiveData
}

fun <T> LiveData<T>.observeWith(owner: LifecycleOwner, observer: (T)->Unit) =
        observe(owner, Observer { observer(it!!) })