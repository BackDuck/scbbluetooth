package com.example.scbbluetooth.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.scbbluetooth.data.database.dao.StateDao
import com.example.scbbluetooth.data.database.dao.WorktimeDao
import com.example.scbbluetooth.data.database.entity.StateEntity
import com.example.scbbluetooth.data.database.entity.WorktimeEntity

@Database(entities = [StateEntity::class, WorktimeEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun stateDao(): StateDao

    abstract fun worktimeDao(): WorktimeDao

}
