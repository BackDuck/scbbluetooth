package com.example.scbbluetooth.presentation.ui.work

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.scbbluetooth.R
import com.example.scbbluetooth.base.MoxyActivity
import com.example.scbbluetooth.presentation.ui.error.ErrorDialogFragment
import kotlinx.android.synthetic.main.activity_work.*
import moxy.ktx.moxyPresenter
import org.altbeacon.beacon.*
import javax.inject.Inject
import javax.inject.Provider

class WorkActivity : MoxyActivity(), BeaconConsumer,
    WorkView {

    @Inject
    lateinit var presenterProvider: Provider<WorkPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private lateinit var beaconManager: BeaconManager
    private val region = Region("myBeaons", null, null, null)

    override val layout = R.layout.activity_work


    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)

        title = "Трекер времени"
        presenter.prepareChronometer()

        presenter.onFirstLaunch()

        btn_startwork.setOnClickListener {
            presenter.onStartClick(cb_home.isChecked)
        }

        btn_stoptwork.setOnClickListener {
            presenter.onStopClick()
        }

        tv_worktime.setOnChronometerTickListener {
            presenter.onChronometerTick(tv_worktime.base)
        }

        verifyBluetooth()
        setUpBeaconScanner()
    }

    override fun addBeacons() {
        beaconManager.beaconParsers.clear()
        beaconManager.beaconParsers.add(
            BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
        )

    }

    override fun setUpBeaconScanner() {
        beaconManager = BeaconManager.getInstanceForApplication(this)
        addBeacons()
        setForeground()
    }

    override fun setForeground() {
        val builder = Notification.Builder(this)
        // builder.setSmallIcon(R.drawable.ic_launcher)
        builder.setContentTitle("Scanning for Beacons")
        val intent = Intent(this, WorkActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(pendingIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "My Notification Channel ID",
                "My Notification Name", NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "My Notification Channel Description"
            val notificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channel.id)
        }
        beaconManager.enableForegroundServiceScanning(builder.build(), 456)
        beaconManager.setEnableScheduledScanJobs(false)
        beaconManager.backgroundBetweenScanPeriod = 5000
        beaconManager.foregroundBetweenScanPeriod = 5000
    }

    override fun startWatch() {
        tv_worktime.start()
    }

    override fun stopWatch() {
        tv_worktime.stop()
    }

    override fun startWorking(fromHome: Boolean) {
        if (!fromHome)
            tv_current.text = getString(R.string.work)
        else
            tv_current.text = getString(R.string.cb_home)

        cb_home.isClickable = false
        cb_home.buttonTintList = ContextCompat.getColorStateList(this, R.color.darkGray)
        cb_home.setTextColor(ContextCompat.getColor(applicationContext, R.color.darkGray))
        tv_current.setBackgroundResource(R.drawable.status_working)
        btn_startwork.visibility = View.GONE
        btn_stoptwork.visibility = View.VISIBLE
        beaconManager.bind(this)
    }

    override fun stopWorking() {
        cb_home.isClickable = true
        cb_home.isChecked = false
        tv_current.text = getString(R.string.status_default)
        cb_home.buttonTintList = ContextCompat.getColorStateList(this, R.color.darkBlue)
        cb_home.setTextColor(ContextCompat.getColor(applicationContext, R.color.darkBlue))
        tv_current.setBackgroundResource(R.drawable.status)
        btn_startwork.visibility = View.VISIBLE
        btn_stoptwork.visibility = View.GONE
        beaconManager.stopMonitoringBeaconsInRegion(region)
        beaconManager.stopRangingBeaconsInRegion(region)
        beaconManager.unbind(this)
    }

    override fun changeTimer(s: String) {
        tv_worktime.text = s
    }

    override fun refreshChrono(b: Long, d: String) {
        tv_date.text = d
        tv_worktime.base = b
        tv_worktime.text = getString(R.string.tv_worktime)
    }

    override fun onDestroy() {
        super.onDestroy()
        beaconManager.unbind(this)
    }

    override fun onBeaconServiceConnect() {
        beaconManager.addMonitorNotifier(object : MonitorNotifier {
            override fun didEnterRegion(region: Region) {
                //Toast.makeText(baseContext, "Вошел в регион", Toast.LENGTH_SHORT).show()
            }

            override fun didExitRegion(region: Region) {
                //Toast.makeText(baseContext, "Вышел из региона", Toast.LENGTH_SHORT).show()
            }

            override fun didDetermineStateForRegion(state: Int, region: Region) {
                println("I have just switched from seeing/not seeing beacons: " + state)
            }
        })
        beaconManager.addRangeNotifier { beacons, _ ->
            if (beacons.isNotEmpty()) {
                //Toast.makeText(baseContext, "Обнаружены биконы", Toast.LENGTH_SHORT).show()

                for (b in beacons) {
                    // TODO: добавить константу uuid для проверки
                    val uuid = b.id1.toString()
                    val major = b.id2.toInt()
                    val minor = b.id3.toInt()
                    val rssi = b.rssi
                    val distance = b.distance.toString()
                    presenter.addBeacon(uuid, rssi, major, minor)
                    Toast.makeText(
                        baseContext,
                        "\nuuid: " + uuid + "\nmajor: " + major + "\nminor: " + minor + "\nrssi: " + rssi + "\ndistance: " + distance,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (beacons.isEmpty()) {
                Toast.makeText(baseContext, "Нет биконов", Toast.LENGTH_SHORT).show()
            }
        }
        try {
            beaconManager.startMonitoringBeaconsInRegion(region)
            beaconManager.startRangingBeaconsInRegion(region)
        } catch (e: RemoteException) {
        }
    }

    private fun verifyBluetooth() {
        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                val dialog = ErrorDialogFragment()
                dialog.show(supportFragmentManager, "Error")
            }
        } catch (e: RuntimeException) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Bluetooth LE not available")
            builder.setMessage("Sorry, this device does not support Bluetooth LE.")
            builder.setPositiveButton(android.R.string.ok, null)
            builder.setOnDismissListener {
                //finish();
                //System.exit(0);
            }
            builder.show()
        }
    }

    fun onSettingsClick(v: View) {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }

}
