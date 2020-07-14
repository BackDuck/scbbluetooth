package com.example.scbbluetooth.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.scbbluetooth.data.database.entity.StateEntity

@Dao
interface StateDao {

    @Insert
    fun insert(state: StateEntity)

    @Insert
    fun insertAll(vararg states: StateEntity)

    @Query("SELECT * FROM state")
    fun getAll(): List<StateEntity>

    @Query("DELETE FROM state")
    fun removeAll()

    @Update
    fun update(state: StateEntity)
}
