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
    fun startWorking(fromHome: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun stopWorking()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeTimer(s: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun refreshChrono(b: Long, d: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun addBeacons()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setUpBeaconScanner()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setForeground()

}