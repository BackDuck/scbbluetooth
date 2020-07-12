package com.example.scbbluetooth.presentation.ui.work

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface WorkView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startWatch()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun stopWatch()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeStatus(isWorking: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeTimer(s: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun refreshChrono(b: Long, d: String)

}