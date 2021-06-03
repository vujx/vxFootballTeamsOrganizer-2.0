package com.algebra.soccernewtry.runningGame.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.algebra.soccernewtry.runningGame.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabaseRunningGame: RoomDatabase() {

    abstract fun runningGameDao(): RunningGameDao
}