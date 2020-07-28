package com.example.scbbluetooth.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.scbbluetooth.data.database.dao.BeaconDao
import com.example.scbbluetooth.data.database.dao.StateDao
import com.example.scbbluetooth.data.database.entity.BeaconEntity
import com.example.scbbluetooth.data.database.entity.StateEntity

@Database(entities = [StateEntity::class, BeaconEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun stateDao(): StateDao

    abstract fun beaconDao(): BeaconDao

}
