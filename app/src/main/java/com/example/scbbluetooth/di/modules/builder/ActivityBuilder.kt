package com.example.scbbluetooth.di.modules.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.example.scbbluetooth.presentation.ui.RootActivity
import com.example.scbbluetooth.presentation.ui.RootModule
import com.example.scbbluetooth.presentation.ui.RootScope

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [RootModule::class])
    @RootScope
    abstract fun provideRootActivity(): RootActivity

}