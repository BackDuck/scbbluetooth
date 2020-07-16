package com.example.scbbluetooth.data.network

import com.example.scbbluetooth.data.network.pojo.StateResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Request {

    @POST("login")
    fun login()

    @POST("state")
    fun state(@Header("Authorization") token: String,
              state: Int): Single<StateResponse>

    @GET("state")
    fun getState(@Header("Authorization") token: String): Single<StateResponse>

    @POST("BeaconsData") //TODO
    fun setBeaconsData()


}
