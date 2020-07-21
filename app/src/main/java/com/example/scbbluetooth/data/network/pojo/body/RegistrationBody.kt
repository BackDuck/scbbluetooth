package com.example.scbbluetooth.data.network.pojo.body

import com.google.gson.annotations.SerializedName

data class RegistrationBody (
    var login: String,
    var password: String,
    var name: String
)