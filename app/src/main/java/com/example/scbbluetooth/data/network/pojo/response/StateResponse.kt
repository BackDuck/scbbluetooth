package com.example.scbbluetooth.data.network.pojo.response

import com.google.gson.annotations.SerializedName

data class StateResponse (
    @SerializedName("state")
    var state: Int,
    @SerializedName("worktime")
    var worktime: Long
)
