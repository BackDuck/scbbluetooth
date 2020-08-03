package com.example.scbbluetooth.presentation.ui.login

import com.example.scbbluetooth.base.MoxyPresenter
import com.example.scbbluetooth.data.network.Api
import com.example.scbbluetooth.data.network.pojo.body.AuthorizationBody
import com.example.scbbluetooth.data.network.pojo.body.RegistrationBody
import com.example.scbbluetooth.data.network.pojo.response.AuthorizationResponse
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import retrofit2.Response
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor() : MoxyPresenter<LoginView>() {

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
            .subscribe(object : SingleObserver<Response<AuthorizationResponse>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(response: Response<AuthorizationResponse>) {
                    when (response.code()) {
                        200 -> {
                            viewState.saveToken(response.headers()["Authorization"].toString())
                            viewState.changeActivity()
                        }
                        400 -> {
                            viewState.showError("Неверные данные")
                        }
                        401 -> {
                            viewState.showError("Нет такого пользователя")
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    viewState.showError("Ошибка соединения с сервером")
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
            .subscribe(object : SingleObserver<Response<Any>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(response: Response<Any>) {
                    viewState.showError(response.code().toString())
                }

                override fun onError(e: Throwable) {
                    viewState.showError(e.localizedMessage!!)
                }
            })
    }
}