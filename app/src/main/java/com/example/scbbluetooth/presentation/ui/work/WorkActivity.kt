package com.example.scbbluetooth.presentation.ui.work

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.scbbluetooth.R
import com.example.scbbluetooth.base.MoxyActivity
import kotlinx.android.synthetic.main.activity_work.*
import javax.inject.Inject
import javax.inject.Provider

class WorkActivity : MvpAppCompatActivity(), WorkView {

    @InjectPresenter
    lateinit var workPresenter: WorkPresenter

    @ProvidePresenter
    fun provideWorkPresenter() = WorkPresenter()

    private var root: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)

        title = "Трекер времени";
        tv_worktime.text = getString(R.string.tv_worktime);
        btn_startwork.setOnClickListener {
            workPresenter.onButtonClick()
        }
        tv_worktime.setOnChronometerTickListener {
            workPresenter.onChronometerTick(tv_worktime.base)
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
            if(cb_home.isChecked){
                tv_current.text = getString(R.string.cb_home)
            }
            else {
                tv_current.text = getString(R.string.work)
            }
            cb_home.isClickable = false
            cb_home.buttonTintList = ContextCompat.getColorStateList(this, R.color.darkGray)
            cb_home.setTextColor(ContextCompat.getColor(applicationContext, R.color.darkGray))
            tv_current.setBackgroundResource(R.drawable.status_working)
            btn_startwork.setBackgroundResource(R.drawable.pink_button)
            btn_startwork.text = "Закончить работу"
        }
        else {
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