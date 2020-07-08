package com.example.scbbluetooth.base

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType


interface BaseView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSnackBar(text: String, duration: Int)

}