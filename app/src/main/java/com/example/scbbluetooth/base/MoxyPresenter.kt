package com.example.scbbluetooth.base

import android.content.Context
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView

abstract class MoxyPresenter<T: MvpView>: MvpPresenter<T>(){

    protected var destroyDisposable = CompositeDisposable()
    protected lateinit var context: Context

    protected fun addDisposable(disposable: Disposable) {
        destroyDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!destroyDisposable.isDisposed)
            destroyDisposable.dispose()
    }

    protected fun Disposable.disposeWhenDestroy(): Boolean {
        return destroyDisposable.add(this)
    }


    fun <T> Single<T>.subscribeAndAddDisposable(success: (T) -> Unit, error: (Throwable) -> Unit) {
        destroyDisposable.add(subscribe({
            success(it)
        }, {
            error(it)
        }))
    }
}
