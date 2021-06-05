package com.algebra.soccernewtry.player.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.algebra.soccernewtry.player.model.Player
import com.algebra.soccernewtry.player.model.PlayerSpecification

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: Player)

    @Query("DELETE FROM AllPlayers WHERE id = :id")
    fun deletePlayer(id: Int)

    @Query("SELECT * FROM AllPlayers")
    fun getAllPlayers(): LiveData<List<Player>>

    @Query("SELECT * FROM AllPlayers")
    suspend fun getAllPlayersForStat(): List<Player>

    @Update
    fun updatePlayer(player: Player)

    @Query("SELECT ap.id AS id, ap.name AS name, ap.bonusPoints AS bonusPoints, COUNT(mp.matchId) AS attendance FROM AllPlayers ap\n" +
            "LEFT JOIN MatchPlayer mp ON mp.playerId = ap.id\n" +
            "WHERE ap.id = :id")
    suspend fun getAllPlayerStat(id: Int): PlayerSpecification

    @Query("SELECT COUNT(*) FROM MatchFlow WHERE goalgetterId = :id")
    suspend fun getNumberOfGoals(id: Int): Int

    @Query("SELECT COUNT(*) FROM MatchFlow WHERE assisterId = :id")
    suspend fun getNumberOfAssist(id: Int): Int
}