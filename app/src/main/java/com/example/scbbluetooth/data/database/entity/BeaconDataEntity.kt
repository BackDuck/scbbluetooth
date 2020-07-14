package com.example.scbbluetooth.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beacons")
data class BeaconDataEntity (

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var uuid: String,

    var rssi: Int,

    var major: Int,

    var minor: Int
)
