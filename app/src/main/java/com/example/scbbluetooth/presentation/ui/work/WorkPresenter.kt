package com.example.scbbluetooth.presentation.ui.work

import android.os.SystemClock
import com.example.scbbluetooth.base.MoxyPresenter
import com.example.scbbluetooth.data.database.AppDatabase
import com.example.scbbluetooth.data.database.entity.BeaconEntity
import com.example.scbbluetooth.data.database.entity.WorktimeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import moxy.InjectViewState
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@InjectViewState
class WorkPresenter @Inject constructor() : MoxyPresenter<WorkView>() {

    private var status = 1
    private var workTimeInSec: Long = 0

    private lateinit var wtEntity: WorktimeEntity

    @Inject
    lateinit var database: AppDatabase

    fun onFirstLaunch() {
        runBlocking(Dispatchers.Default) {
            database.worktimeDao().insert(WorktimeEntity(0, 1, 0))
        }
    }

    fun prepareChronometer() {
        runBlocking(Dispatchers.Default) {
            wtEntity = database.worktimeDao().get() ?: WorktimeEntity(0, 1, 0)
        }

        runBlocking(Dispatchers.Default) {
            wtEntity = database.worktimeDao().get() ?: WorktimeEntity(0, 1, 0)
        }
        val calendar: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd.MM.yy")
        val currentDate: String = sdf.format(calendar.time)
        viewState.refreshChrono(
            SystemClock.elapsedRealtime() - wtEntity.worktime * 1000,
            currentDate
        )
    }

    fun onStartClick(fromHome: Boolean) {
        prepareChronometer()
        viewState.startWatch()
        if (fromHome) {
            wtEntity.state = 3
            status = 3
            viewState.startWorking(true)
        } else {
            wtEntity.state = 2
            status = 2
            viewState.startWorking(false)
        }
        runBlocking(Dispatchers.Default) {
            database.worktimeDao().update(wtEntity)
        }
    }

    fun onStopClick() {
        viewState.stopWatch()
        wtEntity.state = 1
        viewState.stopWorking()
        runBlocking(Dispatchers.Default) {
            database.worktimeDao().update(wtEntity)
        }
    }

    fun onChronometerTick(base: Long) {
        val time: Long = SystemClock.elapsedRealtime() - base
        workTimeInSec = time / 1000
        val h = (time / 3600000).toInt()
        val m = (time - h * 3600000).toInt() / 60000
        val s = (time - h * 3600000 - m * 60000).toInt() / 1000

        val workTime = ("0$h").takeLast(2) + " : " +
                ("0$m").takeLast(2) + " : " +
                ("0$s").takeLast(2)

        viewState.changeTimer(workTime)
        wtEntity.worktime = workTimeInSec
        runBlocking(Dispatchers.Default) {
            database.worktimeDao().update(wtEntity)
        }
    }

    fun addBeacon(uuid: String, rssi: Int, major: Int, minor: Int) {
        val beaconEntity = BeaconEntity(0, uuid, rssi, major, minor)
        runBlocking(Dispatchers.Default) {
            database.beaconDao().insert(beaconEntity)
        }
    }

    fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }

}