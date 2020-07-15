package com.example.scbbluetooth.presentation.ui

import android.bluetooth.BluetoothAdapter
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.scbbluetooth.R
import kotlinx.android.synthetic.main.fragment_error.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ErrorFragment : MvpAppCompatFragment(), ErrorView {
    @InjectPresenter
    lateinit var checkInPresenter: ErrorPresenter

    @ProvidePresenter
    fun provideErrorPresenter() = ErrorPresenter()

    private var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_error, container, false)
        root!!.btn_settings.setOnClickListener { onSettingsClick() }

        return root
    }

    override fun onSettingsClick() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled) {
            val intent = Intent(Intent.ACTION_MAIN, null)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            val cn = ComponentName(
                "com.android.settings",
                "com.android.settings.bluetooth.BluetoothSettings"
            )
            intent.component = cn
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}