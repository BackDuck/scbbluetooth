package com.example.scbbluetooth.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.scbbluetooth.data.database.entity.WorktimeEntity


@Dao
interface WorktimeDao {

    @Insert
    fun insert(worktime: WorktimeEntity)

    @Insert
    fun insertAll(vararg worktime: WorktimeEntity)

    @Query("SELECT * FROM worktime LIMIT 1")
    fun get(): WorktimeEntity?

    @Query("DELETE FROM worktime")
    fun removeAll()

    @Update
    fun update(worktime: WorktimeEntity)
}
