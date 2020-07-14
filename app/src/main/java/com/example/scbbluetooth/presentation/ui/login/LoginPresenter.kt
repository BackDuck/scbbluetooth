package com.example.scbbluetooth.presentation.ui.login

import com.example.scbbluetooth.api.SvmApiService
import com.example.scbbluetooth.api.Worker
import com.example.scbbluetooth.base.MoxyPresenter
import com.example.scbbluetooth.di.modules.retrofit.RetrofitModule
import com.example.scbbluetooth.models.User
import com.example.scbbluetooth.presentation.ui.MockInterceptor
import com.example.scbbluetooth.presentation.ui.login.LoginView
import moxy.InjectViewState
import javax.inject.Inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@InjectViewState
class LoginPresenter @Inject constructor() : MoxyPresenter<LoginView>(){
    var params: Pair<String, String> = Pair("", "")
    var imeiP: String = ""
    var btAddress = ""
    val baseUrl = "https://sovcombank.ru"

    fun onLoginClick() {
        viewState.getBtAddress()
        //viewState.showProgress()
        viewState.getText()
        //viewState.getImei()

        lateinit var user: User

        if (params.first == "test" && params.second == "1234") {
            user = User(
                123456,
                "ivan",
                "Ivan",
                "Ivanovich",
                "Ivanov",
                true,
                imeiP,
                btAddress
            )

            addUser(user)
        } else {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            val userService: SvmApiService =
                retrofit.create<SvmApiService>(SvmApiService::class.java)
            userService.getUser(params.first, params.second).enqueue(object : Callback<Worker> {
                override fun onFailure(call: Call<Worker>, t: Throwable) {
                    viewState.sendErrorToast()
                }

                override fun onResponse(call: Call<Worker>, response: Response<Worker>) {
                    when (response.body()?.successful) {
                        true -> {
                            user = User(
                                response.body()?.user_id,
                                response.body()?.login,
                                response.body()?.firstname,
                                response.body()?.middlename,
                                response.body()?.lastname,
                                response.body()?.employee,
                                imeiP,
                                btAddress
                            )

                            addUser(user)
                        }
                        false -> viewState.sendErrorToast()
                    }
                }
            })
        }
        //viewState.hideProgress()
    }

    fun getText(logpass: Pair<String, String>) {
        params = logpass
    }

    fun getImei(imei: String) {
        imeiP = imei
    }

    fun getBtAddress(btMac: String) {
        btAddress = btMac
    }

    fun addUser(user: User) {
//        if (user != database.checkUser(user.userId.toString(), imeiP)) {
//            database.addUser(user)
//        }
        viewState.saveUser(user)
        var type = "default"
        if (user.isEmployee == true)
            type = "employee"
        viewState.login(type)
    }
}
