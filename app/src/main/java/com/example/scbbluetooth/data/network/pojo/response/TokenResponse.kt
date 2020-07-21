package com.example.scbbluetooth.data.network.pojo.response

import com.google.gson.annotations.SerializedName

data class TokenResponse (
    @SerializedName("token")
    var token: String
)
