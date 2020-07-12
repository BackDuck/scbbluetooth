package com.example.scbbluetooth.base

import android.content.Context
import moxy.MvpPresenter
import moxy.MvpView

abstract class MoxyPresenter<T : MvpView> : MvpPresenter<T>() {

    protected lateinit var context: Context

}