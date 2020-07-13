package com.example.scbbluetooth.presentation.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scbbluetooth.R
import com.example.scbbluetooth.base.MoxyActivity
import com.example.scbbluetooth.presentation.ui.login.LoginPresenter
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class LoginActivity : MoxyActivity(),
    LoginView {

    @Inject
    lateinit var presenterProvider: Provider<LoginPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override val layout = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}