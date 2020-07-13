package com.example.scbbluetooth.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.scbbluetooth.data.database.entity.WorktimeEntity

@Dao
interface WorktimeDao {

    @Insert
    fun insert(worktime: WorktimeEntity)

    @Insert
    fun insertAll(vararg worktime: WorktimeEntity)

    @Query("SELECT * FROM worktime")
    fun getAll(): List<WorktimeEntity>

    @Query("DELETE FROM worktime")
    fun removeAll()
}
