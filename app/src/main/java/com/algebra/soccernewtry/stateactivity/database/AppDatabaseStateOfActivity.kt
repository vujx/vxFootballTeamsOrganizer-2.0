package com.algebra.soccernewtry.stateactivity.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.algebra.soccernewtry.stateactivity.model.StateOfActivity

@Database(entities = [StateOfActivity::class], version = 1)
abstract class AppDatabaseStateOfActivity: RoomDatabase() {

    abstract fun stateActivityDao(): StateActivityDao
}