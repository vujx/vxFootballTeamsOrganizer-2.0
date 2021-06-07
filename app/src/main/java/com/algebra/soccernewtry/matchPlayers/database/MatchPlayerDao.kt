package com.algebra.soccernewtry.matchPlayers.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.soccernewtry.matchPlayers.model.MatchPlayer

@Dao
interface MatchPlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMatchPlayer(matchPlayer: MatchPlayer)

    @Query("SELECT * FROM MatchPlayer")
    fun getAllMatchPlayers(): LiveData<List<MatchPlayer>>

    @Query("DELETE FROM MatchPlayer")
    fun deleteAllMatchPlayers()

    @Query("SELECT * FROM MatchPlayer")
    suspend fun getAll(): List<MatchPlayer>

}