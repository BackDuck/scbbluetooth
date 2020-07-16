package com.example.scbbluetooth.data.network.pojo

import com.google.gson.annotations.SerializedName

data class AuthorizationResponse (
    @SerializedName("login")
    var login: String,
    @SerializedName("password")
    var password: String
)
