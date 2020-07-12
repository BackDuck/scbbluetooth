package com.example.scbbluetooth.presentation.ui.work

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.scbbluetooth.R
import com.example.scbbluetooth.base.MoxyActivity
import kotlinx.android.synthetic.main.activity_work.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class WorkActivity : MoxyActivity(),
    WorkView {

    @Inject
    lateinit var presenterProvider: Provider<WorkPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override val layout = R.layout.activity_work

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)

        title = "Трекер времени"
        tv_worktime.text = getString(R.string.tv_worktime)
        btn_startwork.setOnClickListener {
            presenter.onButtonClick()
        }
        tv_worktime.setOnChronometerTickListener {
            presenter.onChronometerTick(tv_worktime.base)
        }
    }

    override fun startWatch() {
        tv_worktime.start()
    }

    override fun stopWatch() {
        tv_worktime.stop()
    }

    override fun changeStatus(isWorking: Boolean) {
        if (isWorking) {
            if (cb_home.isChecked) {
                tv_current.text = getString(R.string.cb_home)
            } else {
                tv_current.text = getString(R.string.work)
            }
            cb_home.isClickable = false
            cb_home.buttonTintList = ContextCompat.getColorStateList(this, R.color.darkGray)
            cb_home.setTextColor(ContextCompat.getColor(applicationContext, R.color.darkGray))
            tv_current.setBackgroundResource(R.drawable.status_working)
            btn_startwork.setBackgroundResource(R.drawable.pink_button)
            btn_startwork.text = "Закончить работу"
        } else {
            cb_home.isClickable = true
            cb_home.isChecked = false
            tv_current.text = getString(R.string.status_default)
            cb_home.buttonTintList = ContextCompat.getColorStateList(this, R.color.darkBlue)
            cb_home.setTextColor(ContextCompat.getColor(applicationContext, R.color.darkBlue))
            tv_current.setBackgroundResource(R.drawable.status)
            btn_startwork.setBackgroundResource(R.drawable.blue_button)
            btn_startwork.text = "Начать работу"
        }
    }

    override fun changeTimer(s: String) {
        tv_worktime.text = s
    }

    override fun refreshChrono(b: Long, d: String) {
        tv_date.text = d
        tv_worktime.base = b
    }

}