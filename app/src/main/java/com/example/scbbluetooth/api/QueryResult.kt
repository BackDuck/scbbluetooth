package com.example.scbbluetooth.api

data class Worker(
    var successful: Boolean,
    var user_id: Int,
    var employee: Boolean,
    var login: String,
    var firstname: String,
    var middlename: String,
    var lastname: String,
    var imei: String,
    var btAddress: String
)