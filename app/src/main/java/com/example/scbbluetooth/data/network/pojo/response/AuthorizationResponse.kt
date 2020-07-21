package com.example.scbbluetooth.data.network.pojo.response

import com.google.gson.annotations.SerializedName

data class AuthorizationResponse (
    var error: String?,
    var localized_error: String?
)
