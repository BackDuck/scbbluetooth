package com.example.scbbluetooth.data.network.pojo

import com.google.gson.annotations.SerializedName

data class StateResponse (
    @SerializedName("state")
    var state: Int,
    @SerializedName("worktime")
    var worktime: Long
)
