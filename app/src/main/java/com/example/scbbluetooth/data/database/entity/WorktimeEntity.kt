package com.example.scbbluetooth.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "worktime")
data class WorktimeEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var state: Int,

    var worktime: Long
)
