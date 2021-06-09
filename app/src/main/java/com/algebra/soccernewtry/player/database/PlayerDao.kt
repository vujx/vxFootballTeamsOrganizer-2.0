package com.algebra.soccernewtry.player.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.algebra.soccernewtry.additional.actions.codeDisplay.CodeFragment
import com.algebra.soccernewtry.matchPlayers.model.MatchPlayer
import com.algebra.soccernewtry.player.model.MatchsResult
import com.algebra.soccernewtry.player.model.Player
import com.algebra.soccernewtry.player.model.PlayerSpecification

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: Player)

    @Query("DELETE FROM AllPlayers WHERE id = :id")
    fun deletePlayer(id: Int)

    @Query("DELETE FROM AllPlayers")
    fun deleteAll()

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

    @Query("SELECT COUNT(*) FROM MatchFlow WHERE goalgetterId = :id AND isAutoGoal = 0")
    suspend fun getNumberOfGoals(id: Int): Int

    @Query("SELECT COUNT(*) FROM MatchFlow WHERE assisterId = :id")
    suspend fun getNumberOfAssist(id: Int): Int

    @Query("SELECT COUNT(*) FROM MatchFlow WHERE goalgetterId = :id AND isAutoGoal = 1")
    suspend fun getNumberOfAutogoal(id: Int): Int


    @Query("SELECT * FROM MatchPlayer WHERE playerId = :id")
    suspend fun getPlayersMatches(id: Int): List<MatchPlayer>

    @Query("SELECT matchId, COUNT(*) AS teamGoals, teamId FROM MatchFlow\n" +
            "WHERE teamId = 1 AND matchId = :matchId")
    suspend fun getMatchResult(matchId: Int): MatchsResult

    @Query("SELECT matchId, COUNT(*) AS teamGoals, teamId FROM MatchFlow\n" +
            "WHERE teamId = 2 AND matchId = :matchId")
    suspend fun getMatchResultBlue(matchId: Int): MatchsResult

    @Query("SELECT teamId FROM MatchPlayer WHERE playerId = :idPlayer AND matchId = :matchId")
    suspend fun getPlayerTeamId(matchId: Int, idPlayer: Int): Int

}