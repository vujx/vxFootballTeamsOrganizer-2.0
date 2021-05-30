package com.algebra.soccernewtry.player.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.algebra.soccernewtry.player.model.Player

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: Player)

    @Query("DELETE FROM AllPlayers WHERE id = :id")
    fun deletePlayer(id: Int)

    @Query("SELECT * FROM AllPlayers")
    fun getAllPlayers(): LiveData<List<Player>>

    @Query("SELECT * FROM AllPlayers")
    fun getAllPlayersForStat(): List<Player>

    @Update
    fun updatePlayer(player: Player)
}