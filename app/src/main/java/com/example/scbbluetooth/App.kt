package com.example.scbbluetooth

import com.example.scbbluetooth.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<App> {
        return DaggerAppComponent.builder().application(this).create(this)
    }
}