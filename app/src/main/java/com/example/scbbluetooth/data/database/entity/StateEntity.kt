package com.example.scbbluetooth.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "state")
data class StateEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var state: Int,

    var geo_lat: Double,

    var geo_lng: Double
)
