package com.example.scbbluetooth.data.network.pojo.body

import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.RequestBody

data class AuthorizationBody (
    var login: String,
    var password: String
)
{
    fun provideBody(): RequestBody = RequestBody.create(MediaType.parse("application/json"), this.toString())
}