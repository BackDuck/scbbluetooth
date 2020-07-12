package com.example.scbbluetooth.base

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import moxy.MvpAppCompatActivity
import javax.inject.Inject

abstract class MoxyActivity : MvpAppCompatActivity(), ActivityMvpInterface, HasAndroidInjector {

    companion object {
        private const val TAG = "MoxyActivity"
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>


    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    protected abstract val layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layout)
        onViewPrepare(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        onViewDestroy()
    }

    protected open fun onViewPrepare(savedInstanceState: Bundle?) {
    }

    protected open fun onViewDestroy() {
    }

    fun addLifecycleObserver(observer: LifecycleObserver) {
        lifecycle.addObserver(observer)
    }
}