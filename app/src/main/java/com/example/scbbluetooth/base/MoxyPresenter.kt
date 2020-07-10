package com.example.scbbluetooth.base

import android.content.Context
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView

abstract class MoxyPresenter<T : MvpView> : MvpPresenter<T>() {

    protected lateinit var context: Context

}