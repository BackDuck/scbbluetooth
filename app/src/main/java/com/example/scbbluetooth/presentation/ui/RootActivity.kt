package com.example.scbbluetooth.presentation.ui

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.*
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.scbbluetooth.R
import com.example.scbbluetooth.base.MoxyActivity
import com.example.scbbluetooth.utils.FormatByteHelper.findBeaconPattern
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class RootActivity : MoxyActivity(),
    RootView {

    private var mLeScanner: BluetoothLeScanner? = null
    lateinit var btManager: BluetoothManager
    private var btAdapter: BluetoothAdapter? = null
    private lateinit var scanHandler: Handler


    private var settings: ScanSettings? = null
    private lateinit var filter: ArrayList<ScanFilter>
    private var isScanning = false
    private val scanIntervalMs: Long = 5000

    @Inject
    lateinit var presenterProvider: Provider<RootPresenter>

    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    override val layout = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLocationPermission()

        btManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = btManager.adapter

        mLeScanner = btAdapter?.bluetoothLeScanner
        settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()
        filter = ArrayList()

        scanHandler = Handler()
        scanHandler.post(scanRunnable)


    }

    private val scanRunnable = object : Runnable {
        override fun run() {
            if (isScanning) {
                mLeScanner?.stopScan(mScanCallBack)
                Log.i(LOG_TAG, "stop scan")

            } else {
                mLeScanner?.startScan(filter, settings, mScanCallBack)
                Log.i(LOG_TAG, "start scan")
            }
            isScanning = !isScanning
            scanHandler.postDelayed(this, scanIntervalMs)
        }
    }

    private val mScanCallBack = object: ScanCallback(){
        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.e(LOG_TAG, "Scan Failed Error Code: " + errorCode);
        }

        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            Log.i(LOG_TAG, "callbackType " + callbackType)
            var scanRecord = result?.scanRecord?.bytes
            getRssi(result?.rssi)
            findBeaconPattern(scanRecord)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
            if (results != null) {
                for(sr: ScanResult in results.iterator()){
                    Log.i(LOG_TAG, "ScanResult - Results" + sr.toString())
                }
            }
        }
    }

    private fun getRssi(rssi: Int?){
        Log.i(LOG_TAG, rssi.toString())
    }

    fun checkLocationPermission(): Boolean{
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            }
            else{
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION)
            }
            return false
        }else{
            Log.i(LOG_TAG, "persmission granted")
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == MY_PERMISSIONS_REQUEST_LOCATION){
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {}
        }
    }

    companion object {
        private const val LOG_TAG = "Main Activity"
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }

}
