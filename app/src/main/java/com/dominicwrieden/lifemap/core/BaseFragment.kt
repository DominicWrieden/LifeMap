package com.dominicwrieden.lifemap.core

import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable


abstract class BaseFragment : Fragment() {
    protected val disposable = CompositeDisposable()


    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}

