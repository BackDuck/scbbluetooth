package com.example.scbbluetooth.data

import android.content.Context
import androidx.room.Room
import com.example.scbbluetooth.data.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideDatabase(context: Context): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java, "scbBluetooth.db"
    ).build()

}
