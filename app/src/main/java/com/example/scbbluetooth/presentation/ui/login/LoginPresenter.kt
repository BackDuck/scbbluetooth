package com.example.scbbluetooth.presentation.ui.login

import android.preference.PreferenceManager
import com.example.scbbluetooth.base.MoxyPresenter
import com.example.scbbluetooth.data.network.Api
import com.example.scbbluetooth.data.network.pojo.body.AuthorizationBody
import com.example.scbbluetooth.data.network.pojo.response.AuthorizationResponse
import com.example.scbbluetooth.data.network.pojo.body.RegistrationBody
import com.example.scbbluetooth.data.network.pojo.response.TokenResponse
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor() : MoxyPresenter<LoginView>()  {

    @Inject
    lateinit var api: Api

    fun onLoginClick() {
        viewState.getFields()
    }

    fun onForgotClick() {
        viewState.getFields()
    }

    fun login(login: String, password: String) {
        api.login(
            AuthorizationBody(
                login,
                password
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<Response<AuthorizationResponse>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(response: Response<AuthorizationResponse>) {
                    //viewState.showError(response.code().toString())
                    when (response.code()){
                        200 -> {
                            viewState.saveToken(response.headers()["Authorization"].toString())
                            viewState.showSuccess()
                            viewState.changeActivity()
                        }
                        400 -> {
                            viewState.showError("invalid input data")
                        }
                        401 -> {
                            viewState.showError("no user error")
                        }
                    }
                }
                override fun onError(e:Throwable) {
                    viewState.showError("Ошибка")
            }
            })
    }

    fun register(login: String, password: String) {
        api.register(
            RegistrationBody(
                login,
                password,
                login
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<Response<Any>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(response: Response<Any>) {
                    viewState.showError(response.code().toString())
                }
                override fun onError(e:Throwable) {
                    viewState.showError(e.localizedMessage!!)
                }
            })
    }
}