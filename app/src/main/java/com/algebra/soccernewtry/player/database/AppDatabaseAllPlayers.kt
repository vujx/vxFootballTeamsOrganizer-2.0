package com.algebra.soccernewtry.player.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.algebra.soccernewtry.player.model.Player

@Database(entities = [Player::class], version = 1)
abstract class AppDatabaseAllPlayers: RoomDatabase() {
    abstract fun playerDao(): PlayerDao
}