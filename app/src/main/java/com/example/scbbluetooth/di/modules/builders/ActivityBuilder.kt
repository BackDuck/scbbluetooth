package com.example.scbbluetooth.di.modules.builders

import com.example.scbbluetooth.presentation.ui.RootActivity
import com.example.scbbluetooth.presentation.ui.RootModule
import com.example.scbbluetooth.presentation.ui.RootScope
import com.example.scbbluetooth.presentation.ui.WorkScope
import com.example.scbbluetooth.presentation.ui.login.LoginActivity
import com.example.scbbluetooth.presentation.ui.login.LoginModule
import com.example.scbbluetooth.presentation.ui.login.LoginScope
import com.example.scbbluetooth.presentation.ui.work.WorkActivity
import com.example.scbbluetooth.presentation.ui.work.WorkModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [RootModule::class])
    @RootScope
    abstract fun provideRootActivity(): RootActivity

    @ContributesAndroidInjector(modules = [WorkModule::class])
    @WorkScope
    abstract fun provideWorkActivity(): WorkActivity

    @ContributesAndroidInjector(modules = [LoginModule::class])
    @LoginScope
    abstract fun provideLoginActivity(): LoginActivity

}