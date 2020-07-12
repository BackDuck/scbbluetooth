package com.example.scbbluetooth.presentation.ui.work

import android.os.SystemClock
import com.example.scbbluetooth.base.MoxyPresenter
import moxy.InjectViewState
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@InjectViewState
class WorkPresenter @Inject constructor() : MoxyPresenter<WorkView>() {

    private var isWorking = false
    private var fromHome = false

    fun onButtonClick() {
        if (!isWorking) {
            val calendar: Calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd.MM.yy")
            val currentDate: String = sdf.format(calendar.time)
            viewState.refreshChrono(SystemClock.elapsedRealtime(), currentDate)
            viewState.startWatch()
            isWorking = true
            viewState.changeStatus(isWorking)
        } else {
            viewState.stopWatch()
            isWorking = false
            viewState.changeStatus(isWorking)
        }
    }

    fun onChronometerTick(base: Long) {
        val time: Long = SystemClock.elapsedRealtime() - base
        val h = (time / 3600000).toInt()
        val m = (time - h * 3600000).toInt() / 60000
        val s = (time - h * 3600000 - m * 60000).toInt() / 1000

        val workTime = ("0$h").takeLast(2) + " : " +
                ("0$m").takeLast(2) + " : " +
                ("0$s").takeLast(2)

        viewState.changeTimer(workTime)
    }

}