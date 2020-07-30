package com.example.scbbluetooth.presentation.ui.work

import com.example.scbbluetooth.data.database.entity.StateEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface WorkView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startWatch(fromHome: Boolean)

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

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(error: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSuccess()

}