package com.example.scbbluetooth.presentation.ui.login

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.scbbluetooth.R
import com.example.scbbluetooth.base.MoxyActivity
import com.example.scbbluetooth.models.User
import com.example.scbbluetooth.presentation.ui.login.LoginPresenter
import com.example.scbbluetooth.presentation.ui.work.WorkActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class LoginActivity : MoxyActivity(),
    LoginView {

    @Inject
    lateinit var presenterProvider: Provider<LoginPresenter>
    private val loginPresenter by moxyPresenter { presenterProvider.get() }

    override val layout = R.layout.activity_login

    private val REQUEST_PERMISSION_PHONE_STATE = 1

    var imei = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val permissionCheck = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_PHONE_STATE
        )

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_PHONE_STATE),
                REQUEST_PERMISSION_PHONE_STATE
            )
        }

        btn_login.setOnClickListener { loginPresenter.onLoginClick() }
    }

    override fun login(type: String) {
        lateinit var intent: Intent
        if (type == "employee") {
            intent = Intent(this@LoginActivity, WorkActivity::class.java)
            startActivity(intent)
        } else
            Toast.makeText(
                baseContext,
                "Ошибка! Приложение доступно только для работников",
                Toast.LENGTH_LONG
            ).show()
    }

    override fun getText() {
        loginPresenter.getText(Pair(et_login.text.toString(), et_pass.editText?.text.toString()))
    }

    override fun sendErrorToast() {
        Toast.makeText(baseContext, "Ошибка", Toast.LENGTH_LONG).show()
    }

    override fun sendTestToast(data: String) {
        Toast.makeText(baseContext, data, Toast.LENGTH_LONG).show()
    }

    override fun getBtAddress() {
        val SECURE_SETTINGS_BLUETOOTH_ADDRESS = "bluetooth_address"

//        val macAddress: String = Settings.Secure.getString(
//            contentResolver,
//            SECURE_SETTINGS_BLUETOOTH_ADDRESS
//        )

        //loginPresenter.getBtAddress(macAddress)
    }

    override fun saveUser(user: User) {
        val mPrefs = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(user)
        prefsEditor.putString("CurrentUser", json)
        prefsEditor.apply()
    }
}