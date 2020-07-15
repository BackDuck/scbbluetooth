package com.example.scbbluetooth.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.scbbluetooth.data.database.entity.BeaconEntity

@Dao
interface BeaconDao {

    @Insert
    fun insert(beacon: BeaconEntity)

    @Insert
    fun insertAll(vararg beacon: BeaconEntity)

    @Query("SELECT * FROM beacon")
    fun getAll(): List<BeaconEntity>

    @Query("DELETE FROM beacon")
    fun removeAll()

}