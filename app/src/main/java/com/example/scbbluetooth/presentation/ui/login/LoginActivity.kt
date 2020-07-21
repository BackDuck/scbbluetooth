package com.example.scbbluetooth.presentation.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.scbbluetooth.R
import com.example.scbbluetooth.base.MoxyActivity
import com.example.scbbluetooth.presentation.ui.work.WorkActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_work.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class LoginActivity : MoxyActivity(),
    LoginView {

    @Inject
    lateinit var presenterProvider: Provider<LoginPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override val layout = R.layout.activity_login

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)

        et_login.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tv_error.visibility = View.INVISIBLE
                if (s.toString().isNotEmpty() && et_pass.text.length == 4) {
                    btn_login.background = getDrawable(R.drawable.login_active)
                    btn_login.isEnabled = true
                }
                else {
                    btn_login.background = getDrawable(R.drawable.login_inactive)
                    btn_login.isEnabled = false
                }
            }
        })

        et_pass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tv_error.visibility = View.INVISIBLE
                if (s.toString().length == 4 && et_login.text.toString().isNotEmpty()) {
                    btn_login.background = getDrawable(R.drawable.login_active)
                    btn_login.isEnabled = true
                }
                else {
                    btn_login.background = getDrawable(R.drawable.login_inactive)
                    btn_login.isEnabled = false
                }
            }
        })

        btn_login.setOnClickListener {
            presenter.onLoginClick()
        }
        tv_forgot.setOnClickListener {
            presenter.onForgotClick()
        }
    }

    override fun getFields() {
        //presenter.register(et_login.text.toString(), et_pass.text.toString())
        presenter.login(et_login.text.toString(), et_pass.text.toString())
    }

    override fun showError(error: String) {
        tv_error.visibility = View.VISIBLE
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
    }

    override fun saveToken(token: String) {
        val mPrefs = getSharedPreferences("Account", MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        prefsEditor.putString("TOKEN", token)
        prefsEditor.apply()
        Toast.makeText(this, mPrefs.getString("TOKEN", "no value"), Toast.LENGTH_SHORT).show()
    }

    override fun changeActivity() {
        val intent: Intent = Intent(this@LoginActivity, WorkActivity::class.java)
        startActivity(intent)
    }

}