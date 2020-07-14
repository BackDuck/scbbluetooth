package com.example.scbbluetooth.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.scbbluetooth.data.database.entity.BeaconDataEntity

@Dao
interface BeaconDataDao {

    @Insert
    fun insert(beacon: BeaconDataEntity)

    @Insert
    fun insertAll(vararg beacon: BeaconDataEntity)

    @Query("SELECT * FROM beacons")
    fun getAll(): List<BeaconDataEntity>

    @Query("DELETE FROM beacons")
    fun removeAll()
}
