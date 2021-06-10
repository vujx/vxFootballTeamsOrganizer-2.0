package com.algebra.soccernewtry.matchFlow.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.model.PlayerMatchScore
import com.algebra.soccernewtry.matchFlow.model.MatchFlow

@Dao
interface MatchFlowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatchFlow(matchFlow: MatchFlow)

    @Query("SELECT * FROM MatchFlow")
    fun getAllMatchFlowRepo(): LiveData<List<MatchFlow>>

    @Query("DELETE FROM MatchFlow")
    fun deleteAll()

    @Query("DELETE FROM MatchFlow WHERE id = :id")
    fun deleteMatchFlow(id: Int)

    @Query("SELECT * FROM MatchFlow")
    suspend fun getAllMatchFlow(): List<MatchFlow>

    @Query("SELECT ap.name,\n" +
            "(SELECT COUNT(*) FROM MatchFlow WHERE goalgetterId = :playerId AND isAutogoal = 0 AND matchId = :matchId) AS goals,\n" +
            "(SELECT COUNT(*) FROM MatchFlow WHERE assisterId = :playerId AND isAutogoal = 0 AND matchId = :matchId) AS assists,\n" +
            "(SELECT COUNT(*) FROM MatchFlow WHERE goalgetterId = :playerId AND isAutogoal = 1 AND matchId = :matchId) AS owngoals\n" +
            "FROM AllPlayers ap WHERE ap.id = :playerId")
    suspend fun getResultForCurrentPlayer(playerId: Int, matchId: Int): PlayerMatchScore

    @Query("SELECT playerId FROM MatchPlayer WHERE matchId = :matchId AND teamId = 1")
    suspend fun getRedTeamsPlayers(matchId: Int): List<Int>

    @Query("SELECT playerId FROM MatchPlayer WHERE matchId = :matchId AND teamId = 2")
    suspend fun getBlueTeamsPlayers(matchId: Int): List<Int>

    @Query("SELECT * FROM MatchFlow mf WHERE matchId = :matchId ORDER BY id ASC")
    suspend fun getAllMatchFlowByMacthId(matchId: Int): List<MatchFlow>
}