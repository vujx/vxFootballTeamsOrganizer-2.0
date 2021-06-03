package com.algebra.soccernewtry.historyOfGame.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.algebra.soccernewtry.historyOfGame.model.Match

@Database(entities = [Match::class], version = 1)
abstract class AppDatabaseMatches: RoomDatabase() {

    abstract fun matchesDao(): MatchDao
}